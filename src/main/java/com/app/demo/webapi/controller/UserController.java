package com.app.demo.webapi.controller;

import com.app.demo.aspect.attribute.CheckToken;
import com.app.demo.dto.response.core.ResponseDto;
import com.app.demo.webapi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    private UserService userService;

    @GetMapping("/info")
    @CheckToken
    public ResponseDto getUserInfo() {
        Integer userId = super.getCurrentUserId();
        String mail = super.getCurrentUserEmail();
        return userService.getUserInfo(userId, mail);
    }

    @PatchMapping("/info")
    @CheckToken
    public ResponseDto updateUserInfo(@RequestParam("profileImg") MultipartFile profileImg) {
        Integer userId = super.getCurrentUserId();
        String mail = super.getCurrentUserEmail();
        return userService.updateUserInfo(userId, mail, profileImg);
    }
}
