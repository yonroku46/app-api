package com.app.demo.exception;

/**
 * システムエラー用例外クラス
 *
 * @author y_ha
 */
public class SystemException extends RuntimeException {

    /**
     * シリアルバージョンUID.
     */
    private static final long serialVersionUID = 1654211002236415102L;

    /**
     * エラーコード.
     */
    private final String errorCode;

    /**
     * エラー詳細.
     */
    private final String errorDetail;

    /**
     * コンストラクタ（例外発生時）
     *
     * @param errorCode エラーコード
     * @param message メッセージ
     * @param errorDetail 発生例外のスタックトレース
     */
    public SystemException(String errorCode, String message, String errorDetail) {
        super(message);
        this.errorCode = errorCode;
        this.errorDetail = errorDetail;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public String getErrorDetail() {
        return this.errorDetail;
    }
}