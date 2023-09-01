package com.app.demo.dao.entity;

import java.util.Date;
import lombok.Data;

/**
 * Table: m_social
 */
@Data
public class MSocial {
    /**
     * Column: social_id
     * Type: INT
     */
    private Integer socialId;

    /**
     * Column: owner
     * Type: INT
     */
    private Integer owner;

    /**
     * Column: social_imgs
     * Type: VARCHAR(10000)
     * Default value: []
     */
    private String socialImgs;

    /**
     * Column: contents
     * Type: VARCHAR(1000)
     */
    private String contents;

    /**
     * Column: tags
     * Type: VARCHAR(1000)
     * Default value: []
     */
    private String tags;

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