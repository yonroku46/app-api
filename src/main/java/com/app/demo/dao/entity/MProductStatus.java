package com.app.demo.dao.entity;

import lombok.Data;

/**
 * Table: m_product_status
 */
@Data
public class MProductStatus {
    /**
     * Column: status_id
     * Type: INT
     */
    private Integer statusId;

    /**
     * Column: status_name
     * Type: VARCHAR(1)
     */
    private String statusName;

    /**
     * Column: status_description
     * Type: VARCHAR(100)
     */
    private String statusDescription;
}