package com.app.demo.utils;

import com.app.demo.constants.SecurityConst;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * JWT関連ユティリティー
 *
 * @author y_ha
 */
public class JwtUtils {

    /**
     * ユーザーが正常にログインした後にJwtを生成するHs256アルゴリズムを使用する秘密キーがユーザーパスワードを使用する
     *
     * @param ttlMillis jwt期限切れ時間
     * @param userId
     * @param userName
     * @param mail
     * @param role
     * @return
     */
    public static String createJWT(long ttlMillis, Integer userId, String userName, String mail, Integer role) {
        byte[] secretKeyAsBytes = SecurityConst.JWT_SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        // パスロードのプライベートステートメントを作成
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("userName", userName);
        claims.put("mail", mail);
        claims.put("role", role);
        // 発行人
        Integer subject = userId;
        // JwtBuilderを設置して、jwtのbodyを設定
        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                // jti（JWT ID）を設定
                .setId(UUID.randomUUID().toString())
                // iat:jwtの発行時間
                .setIssuedAt(now)
                // このJWTの主体であるすべての人を代表して、json形式で保存
                .setSubject(subject.toString())
                // 署名に使用する署名アルゴリズムと署名に使用する秘密鍵を設定
                .signWith(Keys.hmacShaKeyFor(secretKeyAsBytes));

        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            //有効期限を設定
            builder.setExpiration(exp);
        }
        return builder.compact();
    }

    /**
     * Token 復元する
     *
     * @param token
     * @return
     */
    public static Claims parseJWT(String token) {
        // 署名の秘密鍵は、生成された署名の秘密鍵と同じ
        byte[] secretKeyAsBytes = SecurityConst.JWT_SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        Claims claims = Jwts.parserBuilder()
                // 署名の秘密鍵を設定
                .setSigningKey(Keys.hmacShaKeyFor(secretKeyAsBytes))
                .build()
                // 解析が必要なjwtを設定
                .parseClaimsJws(token).getBody();
        return claims;
    }
}