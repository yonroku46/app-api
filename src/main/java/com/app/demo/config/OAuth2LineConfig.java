package com.app.demo.config;


import com.app.demo.utils.PasswordUtils;
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
@ConfigurationProperties(prefix = "oauth2.line")
public class OAuth2LineConfig {

    public static final String LINE_TOKEN_URL = "https://api.line.me/oauth2/v2.1/token";

    public static final String BASE_CODE_URL = "https://access.line.me/oauth2/v2.1/authorize";

    public static final String PROFILE_ENDPOINT = "https://api.line.me/v2/profile";

    private String GRANT_TYPE = "authorization_code";

    private String CLIENT_ID;

    private String CLIENT_SECRET;

    private String REDIRECT_URI;

    private List<String> scope;

    /**
     * LINEログインページにリダイレクトするURI
     *
     * @author y_ha
     */
    public String getCodeUrl() {
        return new StringBuffer()
                .append(BASE_CODE_URL)
                .append("?response_type=code")
                .append("&client_id=").append(CLIENT_ID)
                .append("&redirect_uri=").append(REDIRECT_URI)
                .append("&scope=openid%20profile%20email")
                .append("&state=").append(PasswordUtils.generateRandomKey(8))
                .toString();
    }

    /**
     * 発行されたユーザーのcodeを利用しLINEにAccessTokenを要求
     *
     * @author y_ha
     */
    public Map<String, String> getParams(String code) {
        Map<String, String> params = new HashMap<>();
//        params.put("code", code);
//        params.put("client_id", CLIENT_ID);
//        params.put("client_secret", CLIENT_SECRET);
//        params.put("redirect_uri", REDIRECT_URI);
//        params.put("grant_type", GRANT_TYPE);
        return params;
    }
}
