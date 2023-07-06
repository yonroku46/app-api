package com.app.demo.constants;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Oauth2認証(LINE)関連を定義
 *
 * @author y_ha
 */
@Component
@NoArgsConstructor
public class OAuth2LineConst {

    @Value("${oauth2.line.client-id}")
    private String SYS_CLIENT_ID;

    @Value("${oauth2.line.client-secret}")
    private String SYS_CLIENT_SECRET;

    @Value("${oauth2.line.redirect-uri}")
    private String SYS_REDIRECT_URI;

    @PostConstruct
    public void init() {
        CLIENT_ID = SYS_CLIENT_ID;
        CLIENT_SECRET = SYS_CLIENT_SECRET;
        REDIRECT_URI = SYS_REDIRECT_URI;
    }

    /**
     * 認証タイプ
     */
    public static final String GRANT_TYPE = "authorization_code";

    /**
     * アプリケーションID
     */
    public static String CLIENT_ID;

    /**
     * シークレットキー
     */
    public static String CLIENT_SECRET;

    /**
     * リダイレクトURI
     */
    public static String REDIRECT_URI;

    /**
     * アクセストークン取得パス
     */
    public static final String LINE_TOKEN_URL = "https://api.line.me/oauth2/v2.1/token";

    /**
     * プロフィール情報取得パス
     */
    public static final String LINE_PROFILE_URL = "https://api.line.me/oauth2/v2.1/verify";
}



