package com.app.demo.webapi.controller;

import com.app.demo.config.OAuth2GoogleConfig;
import com.app.demo.config.OAuth2LineConfig;
import com.app.demo.dto.response.core.ResponseDto;
import com.app.demo.webapi.service.impl.Oauth2ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * OAuth2認証関連機能コントローラー
 *
 * @author chanu
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth2/login")
@Slf4j
public class Oauth2Controller {

    private final OAuth2GoogleConfig oAuth2GoogleConfig;

    private final OAuth2LineConfig oAuth2LineConfig;

    private final Oauth2ServiceImpl oAuth2Service;

    @GetMapping("/google/code")
    public ResponseEntity getGoogleCode() {
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(oAuth2GoogleConfig.getCodeUrl());
    }

    @GetMapping("/google/access-token")
    public ResponseDto getGoogleAccessToken(@RequestParam("code") String code) throws JsonProcessingException {
        return oAuth2Service.getGoogleAccessToken(code);
    }

    @GetMapping("/line/code")
    public ResponseEntity getLineCode() {
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(oAuth2LineConfig.getCodeUrl());
    }

    @GetMapping("/line/access-token")
    public ResponseDto getLineAccessToken(@RequestParam("code") String code) throws JsonProcessingException {
        return oAuth2Service.getLineAccessToken(code);
    }
}
