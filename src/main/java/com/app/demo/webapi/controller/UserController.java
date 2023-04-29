package com.app.demo.webapi.controller;

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

@RestController
@RequestMapping("user")
@Slf4j
public class UserController extends BaseController {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private MUserService userService;

    @GetMapping("/info")
    public ResponseDto getUserInfo() {
        try {
            String userId = "a";
            String userMail = "a@a.a";
            return userService.findUserById(userId, userMail);
        } catch (ApplicationException exception) {
            ResponseUtils.isClientError(exception);
            return ResponseUtils.generateDtoSuccessAbnormal(new Information(exception.getErrorCode(), exception.getMessage()), null);
        }
    }
}
