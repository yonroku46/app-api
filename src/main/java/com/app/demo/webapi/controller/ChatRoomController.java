package com.app.demo.webapi.controller;

import com.app.demo.dao.MChatRoomDao;
import com.app.demo.dto.ChatMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatRoomController extends BaseController {

    private final SimpMessagingTemplate template;

    private final MChatRoomDao mChatRoomDao;

    @MessageMapping("/chat/join/{roomId}")
    @SendTo("/sub/chat/{roomId}")
    public void join(@DestinationVariable String roomId, ChatMessageDto chat) {
        chat.setMessage(new StringBuffer()
                .append(chat.getWriter())
                .append("さんが参加しました")
                .toString());
        chat.setWriter(0);
        template.convertAndSend("/sub/chat/" + roomId, chat);
    }

    @MessageMapping("/chat/send/{roomId}")
    @SendTo("/sub/chat/{roomId}")
    public ChatMessageDto send(@DestinationVariable String roomId, ChatMessageDto chat) {
        if (roomId.equals(chat.getRoomId())) {
            return chat;
        } else {
            return null;
        }
    }
}