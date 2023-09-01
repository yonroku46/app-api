package com.app.demo.dto.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * ソーシャル検索情報DTO
 *
 * @author y_ha
 */
@Data
public class SocialFilterReqDto implements Serializable {

    /**
     * シリアルバージョンUID.
     */
    private static final long serialVersionUID = 2072948880507228198L;

    /**
     * 検索キーワード.
     */
    private String keyword;
}