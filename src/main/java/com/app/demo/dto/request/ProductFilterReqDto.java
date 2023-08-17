package com.app.demo.dto.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 商品検索情報DTO
 *
 * @author y_ha
 */
@Data
public class ProductFilterReqDto implements Serializable {

    /**
     * シリアルバージョンUID.
     */
    private static final long serialVersionUID = 2072948880507228998L;

    /**
     * 検索キーワード.
     */
    private String keyword;

    /**
     * 価格from.
     */
    private String fromPrice;

    /**
     * 価格to.
     */
    private String toPrice;

    /**
     * ブランド.
     */
    private String brand;

    /**
     * メインカテゴリー.
     */
    private String mainCategory;

    /**
     * サブカテゴリー.
     */
    private String subCategory;

    /**
     * 状態.
     */
    private Integer status;
}