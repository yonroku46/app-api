package com.app.demo.webapi.controller;

import com.app.demo.config.OAuth2GoogleConfig;
import com.app.demo.dto.response.core.ResponseDto;
import com.app.demo.webapi.service.impl.Oauth2ServiceImpl;
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
@Slf4j
@RequestMapping("/oauth2/login")
@RestController
@RequiredArgsConstructor
public class Oauth2Controller {

    private final OAuth2GoogleConfig oAuth2GoogleConfig;

    private final Oauth2ServiceImpl oauthService;

    @GetMapping("/google/code")
    public ResponseEntity getCode() {
        return ResponseEntity
                .status(HttpStatus.MOVED_TEMPORARILY)
                .body(oAuth2GoogleConfig.getCodeUrl());
    }

    @GetMapping("/google/access-token")
    public ResponseDto getAccessToken(@RequestParam("code") String code) {
        return oauthService.getGoogleAccessToken(code);
    }
}
