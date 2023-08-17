package com.app.demo.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Table: m_user
 */
@Data
@AllArgsConstructor
public class MUserKey {
    /**
     * Column: user_id
     * Type: INT
     */
    private Integer userId;

    /**
     * Column: mail
     * Type: VARCHAR(100)
     */
    private String mail;
}