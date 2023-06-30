package com.app.demo.webapi.controller;

import com.app.demo.aspect.attribute.LoginToken;
import com.app.demo.dto.request.*;
import com.app.demo.dto.response.core.Information;
import com.app.demo.dto.response.core.ResponseDto;
import com.app.demo.exception.ApplicationException;
import com.app.demo.utils.ResponseUtils;
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
        try {
            return userService.login(req);
        } catch (ApplicationException exception) {
            ResponseUtils.isClientError(exception);
            return ResponseUtils.generateDtoSuccessAbnormal(new Information(exception.getErrorCode(), exception.getMessage()), null);
        }
    }

    @PostMapping("/logout")
    public ResponseDto logout() {
        try {
            Integer uid = super.getCurrentUserId();
            String mail = super.getCurrentUserEmail();
            return userService.loginOut(uid, mail);
        } catch (ApplicationException exception) {
            ResponseUtils.isClientError(exception);
            return ResponseUtils.generateDtoSuccessAbnormal(new Information(exception.getErrorCode(), exception.getMessage()), null);
        }
    }

    @PostMapping("/submit")
    public ResponseDto submit(@RequestBody SubmitReqDto req) {
        try {
            return userService.submit(req);
        } catch (ApplicationException exception) {
            ResponseUtils.isClientError(exception);
            return ResponseUtils.generateDtoSuccessAbnormal(new Information(exception.getErrorCode(), exception.getMessage()), null);
        }
    }

    @PostMapping("/recover")
    public ResponseDto recoverMail(@RequestBody RecoverReqDto req) {
        try {
            return userService.recoverMail(req);
        } catch (ApplicationException exception) {
            ResponseUtils.isClientError(exception);
            return ResponseUtils.generateDtoSuccessAbnormal(new Information(exception.getErrorCode(), exception.getMessage()), null);
        }
    }

    @PatchMapping("/recover")
    public ResponseDto recover(@RequestBody RecoverReqDto req) {
        try {
            return userService.recover(req);
        } catch (ApplicationException exception) {
            ResponseUtils.isClientError(exception);
            return ResponseUtils.generateDtoSuccessAbnormal(new Information(exception.getErrorCode(), exception.getMessage()), null);
        }
    }

    @PostMapping("/check")
    public ResponseDto keyCheck(@RequestBody KeyCheckReqDto req) {
        try {
            return userService.keyCheck(req);
        } catch (ApplicationException exception) {
            ResponseUtils.isClientError(exception);
            return ResponseUtils.generateDtoSuccessAbnormal(new Information(exception.getErrorCode(), exception.getMessage()), null);
        }
    }

    @GetMapping("/refreshToken")
    @LoginToken
    public ResponseDto refreshToken() {
        try {
            return userService.refreshToken(request);
        } catch (ApplicationException exception) {
            ResponseUtils.isClientError(exception);
            return ResponseUtils.generateDtoSuccessAbnormal(new Information(exception.getErrorCode(), exception.getMessage()), null);
        }
    }
}