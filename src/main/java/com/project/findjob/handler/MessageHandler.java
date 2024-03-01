package com.project.findjob.handler;

import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

public class MessageHandler extends TextWebSocketHandler {
    private static List<WebSocketSession> list = new ArrayList<>();
}
