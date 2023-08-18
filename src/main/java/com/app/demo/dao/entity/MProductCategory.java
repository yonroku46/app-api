package com.app.demo.dao.entity;

import lombok.Data;

/**
 * Table: m_product_category
 */
@Data
public class MProductCategory {
    /**
     * Column: category_id
     * Type: INT
     */
    private Integer categoryId;

    /**
     * Column: main_category
     * Type: VARCHAR(100)
     */
    private String mainCategory;

    /**
     * Column: sub_category
     * Type: VARCHAR(100)
     */
    private String subCategory;
}