package com.app.demo.dao.entity;

import java.util.Date;
import lombok.Data;

/**
 * Table: m_product_like
 */
@Data
public class MProductLike {
    /**
     * Column: like_id
     * Type: INT
     */
    private Integer likeId;

    /**
     * Column: product_id
     * Type: INT
     */
    private Integer productId;

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