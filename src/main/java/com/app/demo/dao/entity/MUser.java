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
     * Column: user_id
     * Type: INT
     */
    private Integer userId;

    /**
     * Column: uuid
     * Type: VARCHAR(32)
     */
    private String uuid;

    /**
     * Column: suid
     * Type: VARCHAR(50)
     */
    private String suid;

    /**
     * Column: role
     * Type: INT
     * Default value: 1
     */
    private Integer role;

    /**
     * Column: user_name
     * Type: VARCHAR(100)
     */
    private String userName;

    /**
     * Column: password
     * Type: VARCHAR(500)
     */
    private String password;

    /**
     * Column: profile_img
     * Type: VARCHAR(300)
     */
    private String profileImg;

    /**
     * Column: profile_height
     * Type: INT
     */
    private Integer profileHeight;

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
     * Column: latest_login
     * Type: TIMESTAMP
     */
    private Date latestLogin;

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