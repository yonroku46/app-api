package com.app.demo.dao.entity;

import java.util.Date;
import lombok.Data;

/**
 * Table: m_user
 */
@Data
public class MUser {
    /**
     * Column: uid
     * Type: INT
     */
    private Integer uid;

    /**
     * Column: mail
     * Type: VARCHAR(300)
     */
    private String mail;

    /**
     * Column: password
     * Type: TEXT
     */
    private String password;

    /**
     * Column: user_name
     * Type: VARCHAR(100)
     */
    private String userName;

    /**
     * Column: corp_flg
     * Type: BIT
     * Default value: b'0'
     */
    private Boolean corpFlg;

    /**
     * Column: latest_login
     * Type: TIMESTAMP
     */
    private Date latestLogin;

    /**
     * Column: token
     * Type: TEXT
     */
    private String token;

    /**
     * Column: refresh_token
     * Type: TEXT
     */
    private String refreshToken;

    /**
     * Column: delete_flg
     * Type: BIT
     * Default value: b'0'
     */
    private Boolean deleteFlg;
}