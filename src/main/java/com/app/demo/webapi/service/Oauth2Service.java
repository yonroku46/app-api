package com.app.demo.webapi.service;

import com.app.demo.dto.response.core.ResponseDto;

/**
 * OAuth2認証機能サービス
 *
 * @author chanu
 */
public interface Oauth2Service {

    public ResponseDto getGoogleAccessToken(String code);
}
