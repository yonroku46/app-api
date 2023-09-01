package com.app.demo.dao.entity;

import java.util.Date;
import lombok.Data;

/**
 * Table: m_social_comment
 */
@Data
public class MSocialComment {
    /**
     * Column: comment_id
     * Type: INT
     */
    private Integer commentId;

    /**
     * Column: social_id
     * Type: INT
     */
    private Integer socialId;

    /**
     * Column: reply
     * Type: INT
     */
    private Integer reply;

    /**
     * Column: user_id
     * Type: INT
     */
    private Integer userId;

    /**
     * Column: contents
     * Type: VARCHAR(1000)
     */
    private String contents;

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