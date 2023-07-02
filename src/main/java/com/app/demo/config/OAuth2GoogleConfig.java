package com.app.demo.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "oauth2.google")
public class OAuth2GoogleConfig {
    public static final String GOOGLE_TOKEN_URL = "https://oauth2.googleapis.com/token";
    public static final String BASE_CODE_URL = "https://accounts.google.com/o/oauth2/v2/auth";

    private String grantType = "authorization_code";
    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private List<String> scope;

    /**
     * GoogleログインページにリダイレクトするURI。
     *
     * @author chanu
     */
    public String getCodeUrl() {
        return new StringBuffer()
                .append(BASE_CODE_URL)
                .append("?client_id=").append(clientId)
                .append("&redirect_uri=").append(redirectUri)
                .append("&response_type=code")
                .append("&scope=").append(String.join(" ", scope))
                .toString();
    }

    /**
     * 発行されたユーザーのcodeを利用しGoogleにAccess Tokenを要求
     *
     * @author chanu
     */
    public Map<String, String> getParams(String code) {
        Map<String, String> params = new HashMap<>();
        params.put("code", code);
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);
        params.put("redirect_uri", redirectUri);
        params.put("grant_type", grantType);
        return params;
    }
}
