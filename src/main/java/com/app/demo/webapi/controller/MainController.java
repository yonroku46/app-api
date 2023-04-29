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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class MainController extends BaseController {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private MUserService userService;

    @GetMapping("/check")
    public ResponseDto serverCheck() {
        try {
            return null;
        } catch (ApplicationException exception) {
            ResponseUtils.isClientError(exception);
            return ResponseUtils.generateDtoSuccessAbnormal(new Information(exception.getErrorCode(), exception.getMessage()), null);
        }
    }


    @PostMapping Mapping("/login")
    public ResponseDto login() {
        try {
            return null;
        } catch (ApplicationException exception) {
            ResponseUtils.isClientError(exception);
            return ResponseUtils.generateDtoSuccessAbnormal(new Information(exception.getErrorCode(), exception.getMessage()), null);
        }
    }
}
