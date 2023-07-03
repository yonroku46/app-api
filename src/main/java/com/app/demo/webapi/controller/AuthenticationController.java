package com.app.demo.webapi.controller;

import com.app.demo.aspect.attribute.LoginToken;
import com.app.demo.dto.request.KeyCheckReqDto;
import com.app.demo.dto.request.LoginReqDto;
import com.app.demo.dto.request.RecoverReqDto;
import com.app.demo.dto.request.SubmitReqDto;
import com.app.demo.dto.response.core.ResponseDto;
import com.app.demo.webapi.service.MUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * セキュリティ関連機能コントローラー
 *
 * @author y_ha
 */
@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthenticationController extends BaseController {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private MUserService userService;

    @PostMapping("/login")
    public ResponseDto login(@RequestBody LoginReqDto req) {
        return userService.login(req);
    }

    @PostMapping("/logout")
    public ResponseDto logout() {
        Integer uid = super.getCurrentUserId();
        String mail = super.getCurrentUserEmail();
        return userService.logout(uid, mail);
    }

    @PostMapping("/submit")
    public ResponseDto submit(@RequestBody SubmitReqDto req) {
        return userService.submit(req);
    }

    @PostMapping("/recover")
    public ResponseDto recoverMail(@RequestBody RecoverReqDto req) {
        return userService.recoverMail(req);
    }

    @PatchMapping("/recover")
    public ResponseDto recover(@RequestBody RecoverReqDto req) {
        return userService.recover(req);
    }

    @PostMapping("/check")
    public ResponseDto keyCheck(@RequestBody KeyCheckReqDto req) {
        return userService.keyCheck(req);
    }

    @GetMapping("/refresh-token")
    @LoginToken
    public ResponseDto refreshToken() {
        return userService.refreshToken(request);
    }
}