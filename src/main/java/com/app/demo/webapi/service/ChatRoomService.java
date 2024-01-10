package com.app.demo.webapi.service;

import com.app.demo.dto.ChatMessageDto;
import com.app.demo.dto.response.core.ResponseDto;

public interface ChatRoomService {

    void saveMessage(Integer userId, String roomId, ChatMessageDto chat);

    void sendMessage(Integer userId, String roomId, ChatMessageDto chat);

    void joinChatRoom(Integer userId, String roomId, ChatMessageDto chat);

    ResponseDto createChatRoom(Integer userId);

    ResponseDto inviteChatRoom(Integer userId, String roomId);

    ResponseDto exitChatRoom(Integer userId, String roomId);

    ResponseDto getChatRoomList(Integer userId);

    ResponseDto getChatData(Integer userId, String roomId, Integer offset, Integer size);
}
