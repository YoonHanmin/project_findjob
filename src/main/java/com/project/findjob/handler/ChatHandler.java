package com.project.findjob.handler;

import com.project.findjob.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class ChatHandler extends TextWebSocketHandler {
    private final ChatRepository chatRepository;

//    현재 소켓에 연결된 session을 담는 리스트
    private static List<WebSocketSession>list = new ArrayList<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception{
        String payload = message.getPayload();
        log.info("payload ==> "+payload);

        for (WebSocketSession sess : list){
            sess.sendMessage(message);
            log.info(" 리스트 출력 ==> "+sess.getPrincipal().getName());
        }

    }

//    클라이언트 접속시 호출되는 메서드
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception{
        list.add(session);
        log.info("@#클라이언트 접속 ==>"+session);
    }


//    클라이언트 접속 해제시 호출 메소드
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception{
        log.info("@# 클라이언트 접속 해제 ==>"+session);
        list.remove(session);
    }



}
