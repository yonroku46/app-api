package com.app.demo.constants;

/**
 * セキュリティー関連を定義
 *
 * @author y_ha
 */
public class SecurityConst {

    /**
     * インスタンス生成不可
     */
    private SecurityConst() {
    }

    /**
     * JWT署名鍵
     */
    public static final String JWT_SECRET_KEY = "ZnItc4ktcmRzLWJlbWFjMi6jbHVzdGVyLWNibXpmZmltbWV8eS5hcC1ub3J0aGVhc3QtMS5yZHMuYW1hem9uYXdzLmNvbQ==";

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
     * JWTリフレッシュ 有効時間 60min
     */
    public static final Long REFRESH_EXPIRATION_TIME = 1000 * 60 * 60L;

    /**
     * 言語種別
     */
    public static final String LANGUAGE = "Accept-Language";
}



