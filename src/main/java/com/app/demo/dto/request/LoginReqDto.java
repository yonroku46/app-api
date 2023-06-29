package com.app.demo.dto.request;

import lombok.Data;

import java.io.Serializable;

/**
 * ユーザーログイン情報DTO
 *
 * @author y_ha
 */
@Data
public class LoginReqDto implements Serializable {

    /**
     * シリアルバージョンUID.
     */
    private static final long serialVersionUID = 2072948880507228984L;

    /**
     * メールアドレス.
     */
    private String mail;

    /**
     * パスワード.
     */
    private String password;
}