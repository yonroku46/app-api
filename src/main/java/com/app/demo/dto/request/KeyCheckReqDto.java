package com.app.demo.dto.request;

import lombok.Data;

import java.io.Serializable;

/**
 * ユーザー会員登録情報DTO
 *
 * @author y_ha
 * @version 0.0.1
 */
@Data
public class KeyCheckReqDto implements Serializable {

    /**
     * シリアルバージョンUID.
     */
    private static final long serialVersionUID = 2072948880506228989L;


    /**
     * メールアドレス.
     */
    private String mail;

    /**
     * メールアドレスキー.
     */
    private String mailKey;
}