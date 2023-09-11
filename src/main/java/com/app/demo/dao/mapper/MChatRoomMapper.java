package com.app.demo.dao.mapper;

import com.app.demo.dao.entity.MChatRoom;
import com.app.demo.dto.ChatRoomDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MChatRoomMapper {
    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int deleteByPrimaryKey(String roomUuid);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int insert(MChatRoom row);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int insertSelective(MChatRoom row);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    MChatRoom selectByPrimaryKey(String roomUuid);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int updateByPrimaryKeySelective(MChatRoom row);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int updateByPrimaryKey(MChatRoom row);

    List<ChatRoomDto> findAllRooms(@Param("userId") Integer userId);

    ChatRoomDto findRoomById(@Param("userId") Integer userId, @Param("roomUuid") String roomUuid);
}