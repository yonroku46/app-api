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
     * Column: user_name
     * Type: VARCHAR(100)
     */
    private String userName;

    /**
     * Column: roles
     * Type: INT
     * Default value: 0
     */
    private Integer roles;

    /**
     * Column: password
     * Type: VARCHAR(500)
     */
    private String password;

    /**
     * Column: latest_login
     * Type: TIMESTAMP
     */
    private Date latestLogin;

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
     * Column: create_time
     * Type: TIMESTAMP
     * Default value: CURRENT_TIMESTAMP
     */
    private Date createTime;

    /**
     * Column: delete_flg
     * Type: BIT
     * Default value: b'0'
     */
    private Boolean deleteFlg;
}