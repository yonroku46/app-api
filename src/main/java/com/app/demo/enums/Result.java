package com.app.demo.enums;

/**
 * レスポンス返却コードを列挙
 * <PRE>
 * SUCCESS:0
 * FAILED:1
 * </PRE>
 *
 * @author y_ha
 * @version 0.0.1
 */
public enum Result {

    SUCCESS(0),
    FAILED(1),
    ;

    private final int code;

    private Result(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}