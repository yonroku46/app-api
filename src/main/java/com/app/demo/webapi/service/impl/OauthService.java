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
        return restTemplate.postForEntity(oAuth2GoogleConfig.GOOGLE_TOKEN_URL, oAuth2GoogleConfig.getParams(code), String.class);
    }
}
