package com.project.findjob.controller;

import com.project.findjob.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SseController {
    private final NotificationService notificationService;

    @GetMapping(value = "/connect/{userid}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter connect(@PathVariable (name = "userid")String userid,
                              @RequestHeader(value="Last-Event-ID",required = false,defaultValue = "")String lastEventId){

        return notificationService.subscribe(userid, lastEventId);
    }
}
