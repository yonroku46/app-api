package com.app.demo.dao.entity;

import java.util.Date;
import lombok.Data;

/**
 * Table: m_product_history
 */
@Data
public class MProductHistory {
    /**
     * Column: history_id
     * Type: INT
     */
    private Integer historyId;

    /**
     * Column: product_id
     * Type: INT
     */
    private Integer productId;

    /**
     * Column: owner
     * Type: INT
     */
    private Integer owner;

    /**
     * Column: product_imgs
     * Type: VARCHAR(1000)
     * Default value: []
     */
    private String productImgs;

    /**
     * Column: price
     * Type: INT
     */
    private Integer price;

    /**
     * Column: status
     * Type: INT
     */
    private Integer status;

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