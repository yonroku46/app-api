package com.app.demo.webapi.service.impl;

import com.app.demo.config.OAuth2GoogleConfig;
import com.app.demo.dao.MUserDao;
import com.app.demo.dao.entity.MUser;
import com.app.demo.dto.response.core.ResponseDto;
import com.app.demo.webapi.service.MUserService;
import com.app.demo.webapi.service.Oauth2Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * OAuth2認証機能サービス詳細
 *
 * @author chanu
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class Oauth2ServiceImpl implements Oauth2Service {

    private final OAuth2GoogleConfig oAuth2GoogleConfig;
    private final MUserDao mUserDao;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ResponseDto getGoogleAccessToken(String code) throws JsonProcessingException {
        ResponseEntity<String> response = restTemplate.postForEntity(oAuth2GoogleConfig.GOOGLE_TOKEN_URL, oAuth2GoogleConfig.getParams(code), String.class);

        String jwt = objectMapper.readTree(response.getBody())
                .get("id_token")
                .asText();

        Claims payload = Jwts.parserBuilder().build().parseClaimsJws(jwt).getBody();

        // sub : 구글 유저의 고유 식별자
        String sub = payload.getSubject();

        // 구글 이메일
        String email = payload.get("email", String.class);

        // 성
        String famailyName = payload.get("famaily_name", String.class);

        // 이름
        String givenName = payload.get("given_name", String.class);

        // 유저명
        String userName = new StringBuffer()
                .append(famailyName)
                .append(givenName)
                .toString();

        // TODO sub를 string으로 변경
        MUser muser = mUserDao.findUserByPk(sub, email);

        if (muser == null) {
            // 회원가입 로직
        } else {
            // 로그인 로직
        }
        // 토큰 발급
        return null;
    }
}
