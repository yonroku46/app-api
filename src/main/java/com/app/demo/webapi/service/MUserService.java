package com.app.demo.webapi.service;

import com.app.demo.dto.response.core.ResponseDto;

/**
 * ユーザー機能サービス
 *
 * @author y_ha
 * @version 0.0.1
 */
public interface MUserService {

    public ResponseDto findUserById(String userId, String userMail);
}
