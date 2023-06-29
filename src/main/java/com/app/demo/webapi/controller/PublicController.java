package com.app.demo.webapi.controller;

import com.app.demo.aspect.LocaleAspect;
import com.app.demo.constants.MessageIdConst;
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
 * 共通機能コントローラー
 *
 * @author y_ha
 */
@RestController
@Slf4j
public class PublicController extends BaseController {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private MUserService userService;

    @GetMapping("/")
    public ResponseDto serverCheck() {
        try {
            return ResponseUtils.generateDtoSuccess(
                    new Information(MessageIdConst.I_SERVER_RUNNING, messageSource.getMessage(MessageIdConst.I_SERVER_RUNNING, null, LocaleAspect.LOCALE)), null);
        } catch (ApplicationException exception) {
            ResponseUtils.isClientError(exception);
            return ResponseUtils.generateDtoSuccessAbnormal(new Information(exception.getErrorCode(), exception.getMessage()), null);
        }
    }
}
