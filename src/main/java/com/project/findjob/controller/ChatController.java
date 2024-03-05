package com.project.findjob.controller;


import com.project.findjob.model.Chat;
import com.project.findjob.model.ChatList;
import com.project.findjob.model.User;
import com.project.findjob.repository.ChatListRepository;
import com.project.findjob.repository.ChatRepository;
import com.project.findjob.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ChatController {
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final ChatListRepository chatListRepository;

    @GetMapping("/chat/{userid}/{employid}")
    public String chatGET(@PathVariable("userid") String userid,@PathVariable("employid")String employid, Model model, Authentication auth){
        String name = auth.getName();
        Optional<User> userOptional = userRepository.findByUserid(name);
        User user = userOptional.get();


        User toUser = userRepository.findByUname(userid);
        String username = toUser.getUname();
        log.info("@# username ==<"+username);
        String fromId = user.getUname(); // 세션값에서 꺼내온 이름
        String toId = userid; // 쿼리스트링의 채팅 상대방
        String temp = "";
//        유저일경우 사장,유저명 바꿈(chatList는 사장기준)
        if(user.getType().equals("user")){ // user일 경우
            if(!chatListRepository.existsByFromNameAndToName(toId,fromId)){
                ChatList chatList = new ChatList();
                chatList.setFromName(fromId);
                chatList.setToName(toId);
                chatList.setEmployId(Long.parseLong(employid));
                chatListRepository.save(chatList);
            }
        }else { // owner일 경우

            if (!chatListRepository.existsByFromNameAndToName(fromId, toId)) {
                ChatList chatList = new ChatList();
                chatList.setFromName(fromId);
                chatList.setToName(toId);
                chatList.setEmployId(Long.parseLong(employid));
                chatListRepository.save(chatList);
            }
        }
        String toId2 = fromId;
        String fromId2 = toId;
        List<Chat> chats = null;
        if(user.getType().equals("user")){
            chats = chatRepository.findByToNameAndFromNameOrToNameAndFromNameOrderByIdAsc(fromId, toId, fromId2, toId2);
        }else {

            chats = chatRepository.findByToNameAndFromNameOrToNameAndFromNameOrderByIdAsc(toId, fromId, toId2, fromId2);
        }
        if(chats.isEmpty()){
            model.addAttribute("type","new");
            model.addAttribute("uname",username);
            model.addAttribute("employid",employid);
            return "chat";
        }
        log.info("@# chatGet() 실행");
        log.info("@# chats ==>"+ chats);
        model.addAttribute("type","origin");
        model.addAttribute("chats",chats);
        model.addAttribute("uname",username);
        model.addAttribute("employid",employid);
        return "chat";
    }

}
