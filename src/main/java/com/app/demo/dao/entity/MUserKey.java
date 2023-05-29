package com.app.demo.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * Table: m_user
 */
@Data
@AllArgsConstructor
public class MUserKey {
    /**
     * Column: uid
     * Type: INT
     */
    private Integer uid;

    /**
     * Column: mail
     * Type: VARCHAR(100)
     */
    private String mail;
}