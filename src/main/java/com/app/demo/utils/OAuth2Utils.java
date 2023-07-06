package com.app.demo.utils;

import com.app.demo.constants.OAuth2GoogleConst;
import com.app.demo.constants.OAuth2LineConst;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Oauth2認証関連ユティリティー
 *
 * @author y_ha
 */
@Component
public class OAuth2Utils {

    private static final RestTemplate restTemplate = new RestTemplate();

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * LINEリクエスト発行用のヘッダー
     *
     * @author y_ha
     */
    public static HttpHeaders requestHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return headers;
    }

    /**
     * LINEアクセス要求リクエスト用のパラメータ
     *
     * @author y_ha
     */
    public static MultiValueMap<String, String> lineAccessTokenParams(String code) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", OAuth2LineConst.GRANT_TYPE);
        params.add("code", code);
        params.add("redirect_uri", OAuth2LineConst.REDIRECT_URI);
        params.add("client_id", OAuth2LineConst.CLIENT_ID);
        params.add("client_secret", OAuth2LineConst.CLIENT_SECRET);
        return params;
    }

    /**
     * LINEの会員情報取得用のパラメータ
     *
     * @author y_ha
     */
    public static MultiValueMap<String, String> lineProfileParams(String idToken) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("id_token", idToken);
        params.add("client_id", OAuth2LineConst.CLIENT_ID);
        return params;
    }

    /**
     * LINEのアクセス情報取得
     *
     * @author y_ha
     */
    public static Map<String, Object> getLineToken(String code) throws JsonProcessingException {
        HttpEntity<MultiValueMap<String, String>> tokenRequestEntity = new HttpEntity<>(lineAccessTokenParams(code), requestHeaders());
        ResponseEntity<String> res = restTemplate.exchange(
                OAuth2LineConst.LINE_TOKEN_URL, HttpMethod.POST, tokenRequestEntity, String.class);
        return objectMapper.readValue(res.getBody(), new TypeReference<>(){});
    }

    /**
     * LINEの会員情報取得
     *
     * @author y_ha
     */
    public static Map<String, Object> getLineProfile(String idToken) throws JsonProcessingException {
        HttpEntity<MultiValueMap<String, String>> profileRequestEntity = new HttpEntity<>(lineProfileParams(idToken), null);
        ResponseEntity<String> res = restTemplate.exchange(
                OAuth2LineConst.LINE_PROFILE_URL, HttpMethod.POST, profileRequestEntity, String.class);
        return objectMapper.readValue(res.getBody(), new TypeReference<>(){});
    }

    /**
     * 発行されたユーザーのcodeを利用しGoogleにAccessTokenを要求
     *
     * @author chanu
     */
    public Map<String, String> googleAccessParams(String code) {
        Map<String, String> params = new HashMap<>();
        params.put("grant_type", OAuth2GoogleConst.GRANT_TYPE);
        params.put("code", code);
        params.put("client_id", OAuth2GoogleConst.CLIENT_ID);
        params.put("client_secret", OAuth2GoogleConst.CLIENT_SECRET);
        params.put("redirect_uri", OAuth2GoogleConst.REDIRECT_URI);
        return params;
    }
}