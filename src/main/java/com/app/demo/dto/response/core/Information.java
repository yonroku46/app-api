package com.app.demo.dto.response.core;

import lombok.Data;

import java.io.Serializable;

/**
 *
 * @author
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