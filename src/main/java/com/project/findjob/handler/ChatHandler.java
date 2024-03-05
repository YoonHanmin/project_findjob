package com.project.findjob.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.findjob.model.Chat;
import com.project.findjob.model.ChatList;
import com.project.findjob.model.User;
import com.project.findjob.repository.ChatListRepository;
import com.project.findjob.repository.ChatRepository;
import com.project.findjob.repository.UserRepository;
import com.project.findjob.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.net.URI;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class ChatHandler extends TextWebSocketHandler {
    private final ChatRepository chatRepository;
    private final ChatListRepository chatListRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;
//    현재 소켓에 연결된 session을 담는 리스트
    private static List<WebSocketSession>list = new ArrayList<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception{
        String payload = message.getPayload();
        log.info("payload ==> "+payload);


        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(payload);
        String fromName = jsonNode.get("fromName").asText();
        String toName = jsonNode.get("toName").asText();
        String content = jsonNode.get("message").asText();
        String employid = jsonNode.get("employId").asText();
        log.info(fromName);
        User user = userRepository.findByUname(fromName);
        ChatList chatList = null;
        if(user.getType().equals("user")){
           chatList = chatListRepository.findByFromNameAndToName(toName,fromName);
        }else {
            chatList = chatListRepository.findByFromNameAndToName(fromName, toName);
        }
        Long listId = chatList.getId();

        Chat chat = new Chat();
        chat.setChatListId(listId);
        chat.setToName(toName);
        chat.setFromName(fromName);
        chat.setData(content);
        chat.setReadchat(false);
        chat.setTime(new Timestamp(System.currentTimeMillis()));
        chatRepository.save(chat);



        for (WebSocketSession sess : list){
            sess.sendMessage(message);
            log.info(" 리스트 출력 ==> "+sess.getPrincipal().getName());
        }
//        for (WebSocketSession sess : list){
//            ObjectMapper mapper = new ObjectMapper();
//            ObjectNode node = mapper.createObjectNode();
//            node.put("fromName", fromName);
//            node.put("toName", toName);
//            node.put("message", message.getPayload());
//            node.put("isRecipientOnline", userStatusMap.getOrDefault(toName, false));
//            sess.sendMessage(new TextMessage(node.toString()));
//            log.info("리스트 출력 ==> " + sess.getPrincipal().getName());
//        }
        User touser = userRepository.findByUname(toName);
        String toid = touser.getUserid();
        notificationService.sendEvent(toid,"NewMsg",chat);
    }

//    클라이언트 접속시 호출되는 메서드
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception{
        list.add(session);
        log.info("@#클라이언트 접속 ==>"+session);
        URI uri = session.getUri();
        String query = uri.getQuery();
        String[] queryParams = query.split("&");
        String toName = null;
        String fromName = null;
        String employId = null;
        for (String param : queryParams) {
            String[] keyValue = param.split("=");
            if (keyValue.length == 2) {
                if (keyValue[0].equals("toName")) {
                    toName = URLDecoder.decode(keyValue[1], "UTF-8");
                } else if (keyValue[0].equals("fromName")) {
                    fromName = URLDecoder.decode(keyValue[1], "UTF-8");
                } else if (keyValue[0].equals("employId")) {
                    employId = URLDecoder.decode(keyValue[1], "UTF-8");
                }
            }
        }

        chatRepository.markAsReadchatByFromNameAndToName(fromName,toName);
    }


//    클라이언트 접속 해제시 호출 메소드
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception{
        log.info("@# 클라이언트 접속 해제 ==>"+session);

        list.remove(session);

    }



}
