package com.app.demo.webapi.controller;

import com.app.demo.config.OAuth2GoogleConfig;
import com.app.demo.webapi.service.impl.OauthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/oauth2/login")
@RestController
@RequiredArgsConstructor
public class Oauth2Controller {
    private final OAuth2GoogleConfig oAuth2GoogleConfig;
    private final OauthService oauthService;

    @GetMapping("/google/code")
    public ResponseEntity getCode() {
        return ResponseEntity
                .status(302)
                .body(oAuth2GoogleConfig.getCodeUrl());
    }

    @GetMapping("/google/access-token")
    public ResponseEntity getAccessToken(@RequestParam("code") String code) {
        return oauthService.getGoogleAccessToken(code);
    }
}
