package com.app.demo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * レスポンス返却コードを列挙
 * <PRE>
 * SUCCESS:0
 * FAILED:1
 * </PRE>
 *
 * @author y_ha
 */
@Getter
@AllArgsConstructor
public enum Result {
    SUCCESS(0),
    FAILED(1);

    private final int code;
}