package com.app.demo.dao.entity;

import java.util.Date;
import lombok.Data;

/**
 * Table: m_product
 */
@Data
public class MProduct {
    /**
     * Column: product_id
     * Type: INT
     */
    private Integer productId;

    /**
     * Column: current_owner
     * Type: INT
     */
    private Integer currentOwner;

    /**
     * Column: product_name
     * Type: VARCHAR(100)
     */
    private String productName;

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
     * Column: price_sale
     * Type: INT
     */
    private Integer priceSale;

    /**
     * Column: brand
     * Type: VARCHAR(100)
     */
    private String brand;

    /**
     * Column: colors
     * Type: VARCHAR(1000)
     * Default value: []
     */
    private String colors;

    /**
     * Column: status
     * Type: INT
     */
    private Integer status;

    /**
     * Column: size
     * Type: JSON(0)
     */
    private String size;

    /**
     * Column: size_idx
     * Type: INT
     */
    private Integer sizeIdx;

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

    /**
     * Column: gender
     * Type: VARCHAR(1)
     */
    private String gender;

    /**
     * Column: tags
     * Type: VARCHAR(1000)
     * Default value: []
     */
    private String tags;

    /**
     * Column: additional
     * Type: JSON(0)
     */
    private String additional;

    /**
     * Column: history
     * Type: VARCHAR(1000)
     * Default value: []
     */
    private String history;

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