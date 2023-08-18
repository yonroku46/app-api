package com.app.demo.dto.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

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
     * 価格最小値.
     */
    private String minPrice;

    /**
     * 価格最大値.
     */
    private String maxPrice;

    /**
     * ブランド.
     */
    private List<String> brands;

    /**
     * カテゴリー.
     */
    private List<String> category;

    /**
     * 状態.
     */
    private List<Integer> status;
}