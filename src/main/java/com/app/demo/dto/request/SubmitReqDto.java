package com.app.demo.dto.request;

import lombok.Data;

import java.io.Serializable;

/**
 * ユーザー会員登録情報DTO
 *
 * @author y_ha
 */
@Data
public class SubmitReqDto implements Serializable {

    /**
     * シリアルバージョンUID.
     */
    private static final long serialVersionUID = 2072948880507228989L;

    /**
     * ユーザー名.
     */
    private String name;


    /**
     * メールアドレス.
     */
    private String mail;

    /**
     * パスワード.
     */
    private String password;
}