package com.app.demo.webapi.service.impl;

import com.app.demo.dto.ChatMessageDto;
import com.app.demo.dto.response.core.ResponseDto;
import com.app.demo.webapi.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void saveMessage(String roomId, ChatMessageDto chat) {
        // TODO save to DB
    }

    @Override
    public void sendMessage(String roomId, ChatMessageDto chat) {
        // simpMessagingTemplate.convertAndSend("/sub/chat/" + roomId, new ChatMessageDto());
    }

    @Override
    public ResponseDto createChatRoom() {
        // String roomId = generateRoomId();
        // simpMessagingTemplate.convertAndSend("/sub/chat/" + roomId, new ChatMessageDto());
        return null;
    }

    @Override
    public ResponseDto inviteChatRoom(String roomId, Long targetUserId) {
        // simpMessagingTemplate.convertAndSend("/sub/chat/" + roomId, new ChatMessageDto());
        return null;
    }

    @Override
    public ResponseDto joinChatRoom(String roomId) {
        // simpMessagingTemplate.convertAndSend("/sub/chat/" + roomId, new ChatMessageDto());
        return null;
    }

    @Override
    public ResponseDto exitChatRoom(String roomId) {
        // simpMessagingTemplate.convertAndSend("/sub/chat/" + roomId, new ChatMessageDto());
        return null;
    }

    @Override
    public ResponseDto getChatRoomList() {
        // TODO find ChatRoomList from DB
        return null;
    }

    @Override
    public ResponseDto getChatData(String roomId, Integer offset, Integer size) {
        // TODO find ChatData from DB
        return null;
    }
}
