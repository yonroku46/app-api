package com.app.demo.dto.request;

import lombok.Data;

import java.io.Serializable;

/**
 * ユーザーパスワード再設定情報DTO
 *
 * @author y_ha
 */
@Data
public class RecoverReqDto implements Serializable {

    /**
     * シリアルバージョンUID.
     */
    private static final long serialVersionUID = 2072948881507228989L;

    /**
     * メールアドレス.
     */
    private String mail;

    /**
     * パスワード.
     */
    private String password;
}