package com.app.demo.constants;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * セキュリティー関連を定義
 *
 * @author y_ha
 */
@Component
@NoArgsConstructor
public class SecurityConst {

    @Value("${security.jwt.secret-key}")
    private String SECRET_KEY;

    @PostConstruct
    public void init() {
        JWT_SECRET_KEY = SECRET_KEY;
    }

    /**
     * JWT署名鍵
     */
    public static String JWT_SECRET_KEY;

    /**
     * Authorizationリクエストヘッダ
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * アクセストークン
     */
    public static final String TOKEN_HEADER = "Authorization";

    /**
     * リフレッシュトークン
     */
    public static final String REFRESH_TOKEN_HEADER = "RefreshToken";

    /**
     * JWT 有効時間 30min
     */
    public static final Long EXPIRATION_TIME = 1000 * 60 * 30L;

    /**
     * JWTリフレッシュ 有効時間 24h
     */
    public static final Long REFRESH_EXPIRATION_TIME = 1000 * 60 * 60L * 24;

    /**
     * 言語種別
     */
    public static final String LANGUAGE = "Accept-Language";

    /**
     * 会員登録認証パス
     */
    public static final String SUBMIT_AUTH_PATH = "/auth";

    /**
     * 再発行認証パス
     */
    public static final String RECOVER_AUTH_PATH = "/auth/recover";
}



