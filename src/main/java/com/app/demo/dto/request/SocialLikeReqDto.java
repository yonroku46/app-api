package com.app.demo.dto.request;

import lombok.Data;

import java.io.Serializable;

/**
 * ソーシャルお気に入り追加/削除DTO
 *
 * @author y_ha
 */
@Data
public class SocialLikeReqDto implements Serializable {

    /**
     * シリアルバージョンUID.
     */
    private static final long serialVersionUID = 2072948880507229000L;

    /**
     * ソーシャルID.
     */
    private Integer socialId;
}