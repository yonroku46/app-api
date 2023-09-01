package com.app.demo.dao.entity;

import java.util.Date;
import lombok.Data;

/**
 * Table: m_social_like
 */
@Data
public class MSocialLike {
    /**
     * Column: like_id
     * Type: INT
     */
    private Integer likeId;

    /**
     * Column: social_id
     * Type: INT
     */
    private Integer socialId;

    /**
     * Column: user_id
     * Type: INT
     */
    private Integer userId;

    /**
     * Column: create_time
     * Type: TIMESTAMP
     * Default value: CURRENT_TIMESTAMP
     */
    private Date createTime;
}