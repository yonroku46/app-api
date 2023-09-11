package com.app.demo.dao;

import com.app.demo.aspect.LocaleAspect;
import com.app.demo.constants.MessageIdConst;
import com.app.demo.dao.entity.MChatRoom;
import com.app.demo.dao.mapper.MChatRoomMapper;
import com.app.demo.dto.ChatRoomDto;
import com.app.demo.exception.SystemException;
import com.app.demo.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Slf4j
public class MChatRoomDao {

    @Autowired
    MessageSource messageSource;

    @Autowired
    private MChatRoomMapper mChatRoomMapper;

    /**
     * チャットルームを作成
     *
     * @author y_ha
     */
    public int createRoom(MChatRoom chatRoom) {
        try {
            return mChatRoomMapper.insertSelective(chatRoom);
        } catch (Exception exception) {
            final String methodName = "ChatRoomMapper#createRoom";
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("chatRoom", chatRoom);
            String overview = messageSource.getMessage(MessageIdConst.E_SQL_ISSUE, null, LocaleAspect.LOCALE);
            String detail = StringUtils.convertInterfaceErrorMsg(methodName, paramMap, exception);
            log.error(overview + detail);
            throw new SystemException(MessageIdConst.E_SQL_ISSUE, overview, detail);
        }
    }

    /**
     * ユーザーのチャットルーム一覧を取得
     *
     * @author y_ha
     */
    public List<ChatRoomDto> findAllRooms(Integer userId) {
        try {
            return mChatRoomMapper.findAllRooms(userId);
        } catch (Exception exception) {
            final String methodName = "ChatRoomMapper#findAllRooms";
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("userId", userId);
            String overview = messageSource.getMessage(MessageIdConst.E_SQL_ISSUE, null, LocaleAspect.LOCALE);
            String detail = StringUtils.convertInterfaceErrorMsg(methodName, paramMap, exception);
            log.error(overview + detail);
            throw new SystemException(MessageIdConst.E_SQL_ISSUE, overview, detail);
        }
    }

    /**
     * チャットルーム一の情報を取得
     *
     * @author y_ha
     */
    public ChatRoomDto findRoomById(Integer userId, String roomUuid) {
        try {
            return mChatRoomMapper.findRoomById(userId, roomUuid);
        } catch (Exception exception) {
            final String methodName = "ChatRoomMapper#findRoomById";
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("userId", userId);
            paramMap.put("roomUuid", roomUuid);
            String overview = messageSource.getMessage(MessageIdConst.E_SQL_ISSUE, null, LocaleAspect.LOCALE);
            String detail = StringUtils.convertInterfaceErrorMsg(methodName, paramMap, exception);
            log.error(overview + detail);
            throw new SystemException(MessageIdConst.E_SQL_ISSUE, overview, detail);
        }
    }
}