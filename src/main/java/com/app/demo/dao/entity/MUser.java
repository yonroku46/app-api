package com.app.demo.dao.entity;

import java.util.Date;
import lombok.Data;

/**
 * Table: m_user
 */
@Data
public class MUser {
    /**
     * Column: mail
     * Type: VARCHAR(300)
     */
    private String mail;

    /**
     * Column: uid
     * Type: INT
     */
    private Integer uid;

    /**
     * Column: password
     * Type: VARCHAR(500)
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
     * Type: VARCHAR(500)
     */
    private String token;

    /**
     * Column: refresh_token
     * Type: VARCHAR(500)
     */
    private String refreshToken;

    /**
     * Column: mail_key
     * Type: VARCHAR(50)
     */
    private String mailKey;

    /**
     * Column: mail_auth
     * Type: BIT
     * Default value: b'0'
     */
    private Boolean mailAuth;

    /**
     * Column: delete_flg
     * Type: BIT
     * Default value: b'0'
     */
    private Boolean deleteFlg;
}