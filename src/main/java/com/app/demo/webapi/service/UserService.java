package com.app.demo.webapi.service;

import com.app.demo.dto.request.KeyCheckReqDto;
import com.app.demo.dto.request.LoginReqDto;
import com.app.demo.dto.request.RecoverReqDto;
import com.app.demo.dto.request.SubmitReqDto;
import com.app.demo.dto.response.core.ResponseDto;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * ユーザー機能サービス
 *
 * @author y_ha
 */
public interface UserService {

    public ResponseDto login(LoginReqDto req);

    public ResponseDto logout(Integer userId, String mail);

    public ResponseDto submit(SubmitReqDto req);

    public ResponseDto recoverMail(RecoverReqDto req);

    public ResponseDto recover(RecoverReqDto req);

    public ResponseDto refreshToken(HttpServletRequest httpServletRequest);

    public ResponseDto keyCheck(KeyCheckReqDto req);

    public ResponseDto getUserInfo(Integer userId, String mail);

    public ResponseDto updateUserInfo(Integer userId, String mail, MultipartFile profileImg);
}
