package com.app.demo.dto.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 商品履歴情報DTO
 *
 * @author y_ha
 */
@Data
public class ProductHistoryReqDto implements Serializable {

    /**
     * シリアルバージョンUID.
     */
    private static final long serialVersionUID = 2072948880507228999L;

    /**
     * 商品ID.
     */
    private Integer productId;

    /**
     * 商品履歴ID.
     */
    private List<Integer> productIdList;
}