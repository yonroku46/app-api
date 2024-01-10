package com.app.demo.webapi.controller;

import com.app.demo.dto.ChatMessageDto;
import com.app.demo.dto.response.core.ResponseDto;
import com.app.demo.webapi.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ChatRoomController extends BaseController {

    private final ChatRoomService chatRoomService;

    @MessageMapping("/chat/{roomId}/send")
    public void send(@DestinationVariable String roomId, ChatMessageDto chat) {
        Integer userId = super.getCurrentUserId();
        chatRoomService.saveMessage(userId, roomId, chat);
        chatRoomService.sendMessage(userId, roomId, chat);
    }

    @MessageMapping("/chat/{roomId}/join")
    public void joinChatRoom(@DestinationVariable String roomId, ChatMessageDto chat) {
        Integer userId = super.getCurrentUserId();
        chatRoomService.joinChatRoom(userId, roomId, chat);
    }

    @PostMapping("/chat/create")
    public ResponseDto createChatRoom() {
        Integer userId = super.getCurrentUserId();
        return chatRoomService.createChatRoom(userId);
    }

    @PostMapping("/chat/{roomId}/invite")
    public ResponseDto inviteChatRoom(@PathVariable String roomId) {
        Integer userId = super.getCurrentUserId();
        return chatRoomService.inviteChatRoom(userId, roomId);
    }

    @PostMapping("/chat/{roomId}/exit")
    public ResponseDto exitChatRoom(@PathVariable String roomId) {
        Integer userId = super.getCurrentUserId();
        return chatRoomService.exitChatRoom(userId, roomId);
    }

    @GetMapping("/chat/rooms")
    public ResponseDto getChatRoomList() {
        Integer userId = super.getCurrentUserId();
        return chatRoomService.getChatRoomList(userId);
    }

    @GetMapping("/chat/{roomId}")
    public ResponseDto getChatData(@PathVariable String roomId,
                                   @RequestParam(defaultValue = "0") Integer offset,
                                   @RequestParam(defaultValue = "30") Integer size) {
        Integer userId = super.getCurrentUserId();
        return chatRoomService.getChatData(userId, roomId, offset, size);
    }
}