package com.app.demo.dao.entity;

import java.util.Date;
import lombok.Data;

/**
 * Table: m_chat_room
 */
@Data
public class MChatRoom {
    /**
     * Column: room_uuid
     * Type: VARCHAR(32)
     */
    private String roomUuid;

    /**
     * Column: room_type
     * Type: VARCHAR(100)
     */
    private String roomType;

    /**
     * Column: room_name
     * Type: VARCHAR(100)
     */
    private String roomName;

    /**
     * Column: owner
     * Type: INT
     */
    private Integer owner;

    /**
     * Column: participant
     * Type: VARCHAR(1000)
     * Default value: []
     */
    private String participant;

    /**
     * Column: create_time
     * Type: TIMESTAMP
     * Default value: CURRENT_TIMESTAMP
     */
    private Date createTime;

    /**
     * Column: delete_flg
     * Type: BIT
     * Default value: b'0'
     */
    private Boolean deleteFlg;
}