package com.app.demo.constants;

public class SecurityConst {

    /**
     * JWT署名鍵
     */
    public static final String JWT_SECRET_KEY = "ZnItc4ktcmRzLWJlbWFjMi6jbHVzdGVyLWNibXpmZmltbWV8eS5hcC1ub3J0aGVhc3QtMS5yZHMuYW1hem9uYXdzLmNvbQ==";

    /**
     * Authorzationリクエストヘッダを入れて。
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
     * JWT 有効時間-30min
     */
    public static final Long EXPIRATION_TIME = 1000 * 60 * 30L;

    /**
     * JWT リフレッシュ 有効時間
     */
    public static final Long REFRESH_EXPIRATION_TIME = 1000 * 60 * 60L; // 60 min;

    /**
     * 言語種別
     */
    public static final String LANGUAGE = "Accept-Language";
}



