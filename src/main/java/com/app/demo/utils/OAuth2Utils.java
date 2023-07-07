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

import java.util.Map;

/**
 * OAuth2認証関連ユティリティー
 *
 * @author y_ha
 */
@Component
public class OAuth2Utils {

    private static final RestTemplate restTemplate = new RestTemplate();

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * LINE OAuth2認証
     */

    /**
     * LINEリクエスト発行用ヘッダー
     *
     * @author y_ha
     */
    public static HttpHeaders requestHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return headers;
    }

    /**
     * LINEアクセス要求リクエスト用パラメータ
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
     * LINEの会員情報取得用パラメータ
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
     * GoogleOAuth2 認証
     */

    /**
     * Googleアクセス要求リクエスト用パラメータ
     *
     * @author y_ha
     */
    public static MultiValueMap<String, String> googleAccessTokenParams(String code) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", OAuth2GoogleConst.GRANT_TYPE);
        params.add("code", code);
        params.add("redirect_uri", OAuth2GoogleConst.REDIRECT_URI);
        params.add("client_id", OAuth2GoogleConst.CLIENT_ID);
        params.add("client_secret", OAuth2GoogleConst.CLIENT_SECRET);
        return params;
    }

    /**
     * Googleの会員情報取得用パラメータ
     *
     * @author y_ha
     */
    public static HttpHeaders googleProfileHeaders(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", new StringBuffer().append("Bearer ").append(accessToken).toString());
        return headers;
    }

    /**
     * Googleのアクセス情報取得
     *
     * @author y_ha
     */
    public static Map<String, Object> getGoogleToken(String code) throws JsonProcessingException {
        HttpEntity<MultiValueMap<String, String>> tokenRequestEntity = new HttpEntity<>(googleAccessTokenParams(code), requestHeaders());
        ResponseEntity<String> res = restTemplate.exchange(
                OAuth2GoogleConst.GOOGLE_TOKEN_URL, HttpMethod.POST, tokenRequestEntity, String.class);
        return objectMapper.readValue(res.getBody(), new TypeReference<>(){});
    }

    /**
     * Googleの会員情報取得
     *
     * @author y_ha
     */
    public static Map<String, Object> getGoogleProfile(String accessToken) throws JsonProcessingException {
        HttpEntity<MultiValueMap<String, String>> profileRequestEntity = new HttpEntity<>(null, googleProfileHeaders(accessToken));
        ResponseEntity<String> res = restTemplate.exchange(
                OAuth2GoogleConst.GOOGLE_PROFILE_URL, HttpMethod.GET, profileRequestEntity, String.class);
        return objectMapper.readValue(res.getBody(), new TypeReference<>(){});
    }
}