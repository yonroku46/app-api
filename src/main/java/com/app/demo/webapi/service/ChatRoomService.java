package com.app.demo.webapi.service;

import com.app.demo.dto.ChatMessageDto;
import com.app.demo.dto.response.core.ResponseDto;

public interface ChatRoomService {

    void saveMessage(String roomId, ChatMessageDto chat);

    void sendMessage(String roomId, ChatMessageDto chat);

    void joinChatRoom(String roomId, ChatMessageDto chat);

    ResponseDto createChatRoom();

    ResponseDto inviteChatRoom(String roomId, Integer userId);

    ResponseDto exitChatRoom(String roomId);

    ResponseDto getChatRoomList();

    ResponseDto getChatData(String roomId, Integer offset, Integer size);
}
