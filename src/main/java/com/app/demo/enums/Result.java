package com.app.demo.enums;

/**
 *
 * @author
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