package com.app.demo.webapi.service;

import com.app.demo.dto.response.core.ResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * OAuth2認証機能サービス
 *
 * @author chanu
 */
public interface Oauth2Service {

    public ResponseDto getGoogleAccessToken(String code) throws JsonProcessingException;
}
