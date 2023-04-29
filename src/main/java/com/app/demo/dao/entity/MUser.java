package com.app.demo.dao.entity;

import java.util.Date;
import lombok.Data;

/**
 * Table: m_user
 */
@Data
public class MUser {
    /**
     * Column: user_id
     * Type: VARCHAR(100)
     */
    private String userId;

    /**
     * Column: user_pw
     * Type: VARCHAR(100)
     */
    private String userPw;

    /**
     * Column: user_mail
     * Type: VARCHAR(100)
     */
    private String userMail;

    /**
     * Column: user_name
     * Type: VARCHAR(100)
     */
    private String userName;

    /**
     * Column: corp_flg
     * Type: BIT
     */
    private Boolean corpFlg;

    /**
     * Column: latest_login
     * Type: TIMESTAMP
     */
    private Date latestLogin;

    /**
     * Column: delete_flg
     * Type: BIT
     */
    private Boolean deleteFlg;
}