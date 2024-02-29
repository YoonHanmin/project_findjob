package com.project.findjob.controller;


import com.project.findjob.model.Chat;
import com.project.findjob.model.User;
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

    @GetMapping("/chat/{userid}")
    public String chatGET(@PathVariable("userid") String userid, Model model, Authentication auth){
        String name = auth.getName();
        Optional<User> userOptional = userRepository.findByUserid(name);
        User user = userOptional.get();
        String fromId = user.getUname();
        String toId = userid;
        String toId2 = fromId;
        String fromId2 = toId;
        List<Chat> chats =  chatRepository.findByToNameAndFromNameOrToNameAndFromNameOrderByIdAsc(toId,fromId,toId2,fromId2);
        if(chats.isEmpty()){
            model.addAttribute("type","new");
            return "chat";
        }
        log.info("@# chatGet() 실행");
        log.info("@# chats ==>"+ chats);
        model.addAttribute("type","origin");
        model.addAttribute("chats",chats);
        return "chat";
    }

}
