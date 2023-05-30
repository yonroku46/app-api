package com.app.demo.webapi.controller;

import com.app.demo.aspect.attribute.LoginToken;
import com.app.demo.dto.response.core.Information;
import com.app.demo.dto.response.core.ResponseDto;
import com.app.demo.exception.ApplicationException;
import com.app.demo.utils.ResponseUtils;
import com.app.demo.webapi.service.MUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ユーザー機能コントローラー
 *
 * @author y_ha
 * @version 0.0.1
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController extends BaseController {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private MUserService userService;

    @GetMapping("/info")
    @LoginToken
    public ResponseDto getUserInfo() {
        try {
            Integer uid = super.getCurrentUserId();
            String mail = super.getCurrentUserEmail();
            return userService.findUser(uid, mail);
        } catch (ApplicationException exception) {
            ResponseUtils.isClientError(exception);
            return ResponseUtils.generateDtoSuccessAbnormal(new Information(exception.getErrorCode(), exception.getMessage()), null);
        }
    }
}
