package com.app.demo.dto.response.core;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * レスポンス情報クラス
 *
 * @author y_ha
 */
@Data
@AllArgsConstructor
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
}