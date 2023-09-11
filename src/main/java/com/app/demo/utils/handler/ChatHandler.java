package com.app.demo.utils.handler;

import com.app.demo.dto.ChatMessageDto;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;

@Component
public class ChatHandler extends TextWebSocketHandler {

    HashMap<String, WebSocketSession> sessionMap = new HashMap<>();
    Gson gson = new Gson();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        // send message
        String payload = message.getPayload();
        ChatMessageDto chatMessage = gson.fromJson(payload, ChatMessageDto.class);

        for (String key : sessionMap.keySet()) {
            WebSocketSession wss = sessionMap.get(key);
            try {
                wss.sendMessage(new TextMessage(gson.toJson(chatMessage)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // socket connect
        super.afterConnectionEstablished(session);
        sessionMap.put(session.getId(), session);
        ChatMessageDto message = new ChatMessageDto();
        message.setSessionId(session.getId());
        session.sendMessage(new TextMessage(gson.toJson(message)));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // socket disconnect
        sessionMap.remove(session.getId());
        super.afterConnectionClosed(session, status);
    }
}