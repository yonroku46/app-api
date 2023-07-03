package com.app.demo.webapi.service.impl;

import com.app.demo.config.OAuth2GoogleConfig;
import com.app.demo.dto.response.core.ResponseDto;
import com.app.demo.webapi.service.Oauth2Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * OAuth2認証機能サービス詳細
 *
 * @author chanu
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class Oauth2ServiceImpl implements Oauth2Service {

    private final OAuth2GoogleConfig oAuth2GoogleConfig;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public ResponseDto getGoogleAccessToken(String code) {
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(oAuth2GoogleConfig.GOOGLE_TOKEN_URL, oAuth2GoogleConfig.getParams(code), String.class);
        // TODO 受け取ったアクセストークン及びユーザー情報の処理
        return null;
    }
}
