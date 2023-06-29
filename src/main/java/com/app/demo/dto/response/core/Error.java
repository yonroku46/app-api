package com.app.demo.dto.response.core;

import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * レスポンスエラークラス
 *
 * @author y_ha
 */
@EqualsAndHashCode(callSuper = true)
public class Error extends Information implements Serializable {

    private static final long serialVersionUID = 34895794378L;

    /**
     * メソッドパラメータ.
     */
    private String parameter;

    /**
     * エラー詳細.
     */
    private String errorDetail;

    /**
     * コンストラクタ
     *
     * @param messageId メッセージID
     * @param message メッセージ
     */
    public Error(String messageId, String message) {
        super(messageId, message);
    }

    /**
     * コンストラクタ（BindingResult用）
     *
     * @param messageId メッセージID
     * @param message メッセージ
     * @param parameter
     */
    public Error(String messageId, String message, String parameter) {
        super(messageId, message);
        this.parameter = parameter;
    }

    /**
     * コンストラクタ（スタックトレースありの場合）
     *
     * @param messageId メッセージID
     * @param message メッセージ
     * @param parameter パラメータ
     * @param errorDetail エラー詳細
     */
    public Error(String messageId, String message, String parameter, String errorDetail) {
        super(messageId, message);
        this.errorDetail = errorDetail;
    }
}