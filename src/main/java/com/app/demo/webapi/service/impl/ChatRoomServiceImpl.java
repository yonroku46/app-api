package com.app.demo.webapi.service.impl;

import com.app.demo.constants.WebSocketConst;
import com.app.demo.dao.MChatRoomDao;
import com.app.demo.dao.entity.MChatRoom;
import com.app.demo.dto.ChatMessageDto;
import com.app.demo.dto.ChatRoomDto;
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

    private final SimpMessagingTemplate template;

    private final MChatRoomDao mChatRoomDao;

    @Override
    public void saveMessage(Integer userId, String roomId, ChatMessageDto chat) {
        // TODO save to DB
    }

    @Override
    public void sendMessage(Integer userId, String roomId, ChatMessageDto chat) {
        template.convertAndSend(WebSocketConst.CHAT_PATH + roomId, chat);
    }

    @Override
    public void joinChatRoom(Integer userId, String roomId, ChatMessageDto chat) {
        chat.setMessage(new StringBuffer()
                .append(chat.getWriter())
                .append("さんが参加しました")
                .toString());
        chat.setWriter(WebSocketConst.SYSTEM_MESSAGE);
        template.convertAndSend(WebSocketConst.CHAT_PATH + roomId, chat);
    }

    @Override
    public ResponseDto createChatRoom(Integer userId) {
        // TODO after creating a room, return the roomId
        // MChatRoom chatRoom = new MChatRoom();
        // mChatRoomDao.createRoom(chatRoom);
        return null;
    }

    @Override
    public ResponseDto inviteChatRoom(Integer userId, String roomId) {
        // template.convertAndSend(WebSocketConst.CHAT_PATH + roomId, new ChatMessageDto());
        return null;
    }

    @Override
    public ResponseDto exitChatRoom(Integer userId, String roomId) {
        // template.convertAndSend(WebSocketConst.CHAT_PATH + roomId, new ChatMessageDto());
        return null;
    }

    @Override
    public ResponseDto getChatRoomList(Integer userId) {
        // TODO find ChatRoomList from DB
        // List<ChatRoomDto> roomList = mChatRoomDao.findAllRooms(userId);
        return null;
    }

    @Override
    public ResponseDto getChatData(Integer userId, String roomId, Integer offset, Integer size) {
        // TODO find ChatData from DB
//         ChatRoomDto roomInfo = mChatRoomDao.findRoomById(userId, roomId);
        return null;
    }
}