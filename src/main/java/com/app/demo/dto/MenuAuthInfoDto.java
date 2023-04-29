package com.app.demo.dto;

import java.io.Serializable;
import lombok.Data;

/**
 *
 * @author auth
 */
@Data
public class MenuAuthInfoDto implements Serializable {

    /**
     * シリアルバージョンUID.
     */
    private static final long serialVersionUID = 2072948880507228983L;

    /**
     * リクエストマッピング.
     */
    private String requestMapping;

    /**
     * 権限フラグ.
     */
    private Boolean accessibleFlg;
}