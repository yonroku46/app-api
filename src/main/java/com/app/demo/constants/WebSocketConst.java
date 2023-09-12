package com.app.demo.constants;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * WebSocket関連を定義
 *
 * @author y_ha
 */
@Component
@NoArgsConstructor
public class WebSocketConst {

    /**
     * システムメッセージ番号
     */
    public static Integer SYSTEM_MESSAGE = 0;

    /**
     * チャット用パス
     */
    public static final String CHAT_PATH = "/sub/chat/";
}



