package com.app.demo.webapi.service;

import com.app.demo.dto.request.LoginReqDto;
import com.app.demo.dto.response.core.ResponseDto;

import javax.servlet.http.HttpServletRequest;

/**
 * ユーザー機能サービス
 *
 * @author y_ha
 * @version 0.0.1
 */
public interface MUserService {

    public ResponseDto login(LoginReqDto req);

    public ResponseDto loginOut(Integer uid, String mail);

    public ResponseDto refreshToken(HttpServletRequest httpServletRequest);

    public ResponseDto findUserByPk(Integer uid, String mail);

    public int updateUserAccessToken(Integer uid, String mail, String accessToken);
}
