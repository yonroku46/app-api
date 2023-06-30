package com.app.demo.exception;

import com.app.demo.dto.response.core.Information;
import com.app.demo.dto.response.core.ResponseDto;
import com.app.demo.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler
    public ResponseDto applicationExceptionHandle(ApplicationException exception) {
        ResponseUtils.isClientError(exception);
        return ResponseUtils.generateDtoSuccessAbnormal(new Information(exception.getErrorCode(), exception.getMessage()), null);
    }
}
