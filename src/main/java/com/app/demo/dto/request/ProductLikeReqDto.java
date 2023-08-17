package com.app.demo.dto.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 商品お気に入り追加DTO
 *
 * @author y_ha
 */
@Data
public class ProductLikeReqDto implements Serializable {

    /**
     * シリアルバージョンUID.
     */
    private static final long serialVersionUID = 2072948880507229000L;

    /**
     * 商品ID.
     */
    private Integer productId;
}