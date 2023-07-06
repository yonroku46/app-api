package com.app.demo.webapi.controller;

import com.app.demo.dto.response.core.ResponseDto;
import com.app.demo.webapi.service.Oauth2Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class Oauth2Controller extends BaseController {

    private final Oauth2Service oAuth2Service;

    @GetMapping("/google/access-token")
    public ResponseDto getGoogleAccessToken(@RequestParam("code") String code) throws JsonProcessingException {
        return oAuth2Service.getGoogleAccessToken(code);
    }

    @GetMapping("/line/access-token")
    public ResponseDto getLineAccessToken(@RequestParam("code") String code) throws JsonProcessingException {
        return oAuth2Service.getLineAccessToken(code);
    }
}
