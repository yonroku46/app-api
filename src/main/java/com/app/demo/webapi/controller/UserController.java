package com.app.demo.webapi.controller;

import com.app.demo.aspect.attribute.CheckToken;
import com.app.demo.dto.response.core.ResponseDto;
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
    @CheckToken
    public ResponseDto getUserInfo() {
        Integer uid = super.getCurrentUserId();
        String mail = super.getCurrentUserEmail();
        return userService.findUser(uid, mail);
    }
}
