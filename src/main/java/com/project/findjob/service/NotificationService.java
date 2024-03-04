package com.project.findjob.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.findjob.dto.NotificationResponse;
import com.project.findjob.model.Chat;
import com.project.findjob.model.Notification;
import com.project.findjob.repository.EmitterrepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private static final Long DEFAULT_TIMEOUT = 60l * 1000 * 60;
    private final EmitterrepositoryImpl emitterRepository;

    public SseEmitter subscribe(String userid,String lastEventId) {
        String id = userid;
        SseEmitter emitter = emitterRepository.save(id, new SseEmitter(DEFAULT_TIMEOUT));

        // 오류시 emitter 제거
        emitter.onCompletion(()-> emitterRepository.deleteById(id));
        log.info("연결종료!!!! 객체 삭제한 id==>"+id);
        emitter.onTimeout(() -> emitterRepository.deleteById(id));

        // 더미데이터 전송
        sendToclient(emitter,id,"{'key':'"+id+"'}");

        if(!lastEventId.isEmpty()) {
            Map<String,Object> events = emitterRepository.findAllEventCacheStartWithById(String.valueOf(id));
            events.entrySet().stream()
                    .filter(entry -> lastEventId.compareTo(entry.getKey())<0)
                    .forEach(entry -> sendToclient(emitter,entry.getKey(),entry.getValue()));

        }
        return emitter;
    }
    //	클라이언트에게 이벤트 발생시 emitter 보냄
    public void sendEvent(String receiver, String notificationType, Chat chat) {
        Notification notification = createNotification(receiver,notificationType,chat);

        Map<String,SseEmitter> sseEmitters = emitterRepository.findAllEmitterStartWithById(receiver);
        log.info("찾은 emitter :"+sseEmitters);
        sseEmitters.forEach(
                (key,emitter)->{
//					클라이언트와 연결이 끊겼을 경우에 대비해 Cache map에 데이터 저장
                    emitterRepository.saveEventCache(key, notification);
                    log.info("보낸 알림내용 ==>"+notification);
                    //데이터 전송 from()메소드를 통해  Json형식으로 변환한뒤 클라이언트에게 전송
                    sendToclient(emitter, key, NotificationResponse.from(notification));
                }
        );

    }

    //	Notification 객체 생성 메소드
    private Notification createNotification(String receiver,String notificationType,Chat chat) {
        return Notification.builder()
                .receiver(receiver)
                .notificationType(notificationType)
                .chat(chat)
                .time(new Timestamp(System.currentTimeMillis())) //알림 발송 시간을 구해서 전송
                .url("/receive")
                .isRead(false)
                .build();
    }

    private void sendToclient(SseEmitter emitter,String id,Object data) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonData = objectMapper.writeValueAsString(data);
            emitter.send(SseEmitter.event()
                    .id(id)
                    .name("NewMsg")
                    .data(jsonData)); //json형태로 변환하여 전송
            log.info("연결됨!! ==> " + data);

        } catch (IOException e) {
            emitterRepository.deleteById(id);
            throw new RuntimeException("연결오류");
        }

    }

}
