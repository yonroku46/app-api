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
        chatRoomService.saveMessage(roomId, chat);
        chatRoomService.sendMessage(roomId, chat);
    }

    @PostMapping("/chat/create")
    public ResponseDto createChatRoom() {
        return chatRoomService.createChatRoom();
    }

    @PostMapping("/chat/{roomId}/invite")
    public ResponseDto inviteChatRoom(@PathVariable String roomId, @RequestBody Long targetUserId) {
        return chatRoomService.inviteChatRoom(roomId, targetUserId);
    }

    @PostMapping("/chat/{roomId}/join")
    public ResponseDto joinChatRoom(@PathVariable String roomId) {
        return chatRoomService.joinChatRoom(roomId);
    }

    @PostMapping("/chat/{roomId}/exit")
    public ResponseDto exitChatRoom(@PathVariable String roomId) {
        return chatRoomService.exitChatRoom(roomId);
    }

    @GetMapping("/chat/room-list")
    public ResponseDto getChatRoomList() {
        return chatRoomService.getChatRoomList();
    }

    @GetMapping("/chat/{roomId}")
    public ResponseDto getChatData(@PathVariable String roomId,
                                   @RequestParam(defaultValue = "0") Integer offset,
                                   @RequestParam(defaultValue = "30") Integer size) {
        return chatRoomService.getChatData(roomId, offset, size);
    }
}