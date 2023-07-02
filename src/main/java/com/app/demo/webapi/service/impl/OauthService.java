package com.app.demo.webapi.service.impl;

import com.app.demo.config.OAuth2GoogleConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class OauthService {
    private final OAuth2GoogleConfig oAuth2GoogleConfig;
    private final RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity getGoogleAccessToken(String code) {
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(oAuth2GoogleConfig.GOOGLE_TOKEN_URL, oAuth2GoogleConfig.getParams(code), String.class);
        // TODO 受け取ったアクセストークン及びユーザー情報の処理
        return null;
    }
}
