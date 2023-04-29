package com.app.demo.exception;

import org.springframework.http.HttpStatus;

/**
 * 業務エラー用例外クラス
 *
 * @author
 */
public class ApplicationException extends RuntimeException {

    /**
     * シリアルバージョンUID.
     */
    private static final long serialVersionUID = 1491302704911367447L;

    /**
     * HTTPステータス.
     */
    private final HttpStatus status;

    /**
     * エラーコード.
     */
    private final String errorCode;

    /**
     * コンストラクタ
     *
     * @param status HTTPステータス
     * @param errorCode エラーコード
     * @param message エラーメッセージ
     */
    public ApplicationException(HttpStatus status, String errorCode, String message) {
        super(message);
        this.status = status;
        this.errorCode = errorCode;
    }

    public HttpStatus getStatus() {
        return this.status;
    }

    public String getErrorCode() {
        return this.errorCode;
    }
}
