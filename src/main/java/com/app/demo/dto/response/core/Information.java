package com.app.demo.dto.response.core;

import lombok.Data;

import java.io.Serializable;

/**
 * レスポンス情報クラス
 *
 * @author y_ha
 * @version 0.0.1
 */
@Data
public class Information implements Serializable {

    private static final long serialVersionUID = 34895794371L;

    /**
     * メッセージID.
     */
    private String messageId;

    /**
     * メッセージ.
     */
    private String message;

    /**
     * コンストラクタ
     *
     * @param messageId メッセージID
     * @param message メッセージ
     */
    public Information(String messageId, String message) {
        this.messageId = messageId;
        this.message = message;
    }
}