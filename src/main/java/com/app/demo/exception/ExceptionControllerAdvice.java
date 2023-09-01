package com.app.demo.exception;

import com.app.demo.aspect.LocaleAspect;
import com.app.demo.dto.response.core.Information;
import com.app.demo.dto.response.core.ResponseDto;
import com.app.demo.utils.ResponseUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;

    @ExceptionHandler
    public ResponseDto applicationExceptionHandle(ApplicationException exception) {
        ResponseUtils.isClientError(exception);
        return ResponseUtils.generateDtoSuccessAbnormal(new Information(exception.getErrorCode(), exception.getMessage()), null);
    }
    @ExceptionHandler
    public ResponseDto applicationExceptionHandle(JsonProcessingException exception) {
        return ResponseUtils.generateDtoSuccessAbnormal(new Information(null, exception.getMessage()), null);
    }
    @ExceptionHandler
    public ResponseDto applicationExceptionHandle(ExpiredJwtException exception) throws IOException {
        response.sendError((HttpServletResponse.SC_UNAUTHORIZED), exception.getMessage());
        return ResponseUtils.generateDtoSuccessAbnormal(new Information(null, exception.getMessage()), null);
    }
}
