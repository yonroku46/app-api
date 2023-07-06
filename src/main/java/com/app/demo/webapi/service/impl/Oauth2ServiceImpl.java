package com.app.demo.webapi.service.impl;

import com.app.demo.aspect.LocaleAspect;
import com.app.demo.constants.MessageIdConst;
import com.app.demo.constants.SecurityConst;
import com.app.demo.dao.MUserDao;
import com.app.demo.dao.entity.MUser;
import com.app.demo.dto.response.UserInfoResDto;
import com.app.demo.dto.response.core.Information;
import com.app.demo.dto.response.core.ResponseDto;
import com.app.demo.utils.DateUtils;
import com.app.demo.utils.JwtUtils;
import com.app.demo.utils.OAuth2Utils;
import com.app.demo.utils.ResponseUtils;
import com.app.demo.webapi.service.Oauth2Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

/**
 * OAuth2認証機能サービス詳細
 *
 * @author chanu
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class Oauth2ServiceImpl implements Oauth2Service {

    private final MessageSource messageSource;

    private final OAuth2Utils oauth2Utils;

    private final MUserDao mUserDao;

    @Override
    public ResponseDto getGoogleAccessToken(String code) throws JsonProcessingException {
//        ResponseEntity<String> response = restTemplate.postForEntity(oAuth2GoogleConfig.GOOGLE_TOKEN_URL, oAuth2GoogleConfig.getParams(code), String.class);
//
//        String jwt = objectMapper.readTree(response.getBody())
//                .get("id_token")
//                .asText();
//
//        Claims payload = Jwts.parserBuilder().build().parseClaimsJws(jwt).getBody();

        // sub : 구글 유저의 고유 식별자
//        String sub = payload.getSubject();
//
//        // 구글 이메일
//        String email = payload.get("email", String.class);
//
//        // 유저명
//        String userName = new StringBuffer()
//                .append(payload.get("family_name", String.class))
//                .append(payload.get("given_name", String.class))
//                .toString();

        // TODO sub를 string으로 변경
//        MUser user = mUserDao.findUserByMail(email);

//        if (user == null) {
//            // 회원가입 로직
//        } else {
//            // 로그인 로직
//        }
//        // 토큰 발급
        return null;
    }

    @Override
    public ResponseDto getLineAccessToken(String code) throws JsonProcessingException {
        // ユーザーインスタンス
        UserInfoResDto res = new UserInfoResDto();

        // IDトークン取得
        Map<String, Object> tokenMap = oauth2Utils.getLineToken(code);
        String idToken = tokenMap.get("id_token").toString();

        // プロフィール取得
        Map<String, Object> profileMap = oauth2Utils.getLineProfile(idToken);
        String suid = profileMap.get("sub").toString();

        MUser user = mUserDao.findUserBySuid(suid);
        if (user == null) {
            // 新規ユーザー登録
            user = new MUser();
            user.setSuid(suid);
            user.setUserName(profileMap.get("name").toString());
            user.setMail(profileMap.get("email").toString());
            user.setMailAuth(Boolean.TRUE);
            mUserDao.submit(user);
        }

        // ログイン処理
        MUser record = mUserDao.findUserByMail(user.getMail());
        // ログイン日時とプロフィール更新
        LocalDateTime latestLogin = DateUtils.getUTCdatetimeAsDate();
        record.setLatestLogin(Date.from(latestLogin.atZone(ZoneId.systemDefault()).toInstant()));
        record.setUserName(profileMap.get("name").toString());
        record.setProfileImg(profileMap.get("picture").toString());
        mUserDao.updateUserData(record);

        String token = JwtUtils.createJWT(SecurityConst.EXPIRATION_TIME, user.getUserId(), user.getUserName(), user.getMail(), user.getRole());
        String refreshToken = JwtUtils.createJWT(SecurityConst.REFRESH_EXPIRATION_TIME, user.getUserId(), user.getUserName(), user.getMail(), user.getRole());

        res.setUserId(user.getUserId());
        res.setUserName(user.getUserName());
        res.setProfileImg(user.getProfileImg());
        res.setMail(user.getMail());
        res.setToken(token);
        res.setRefreshToken(refreshToken);
        res.setMailAuth(user.getMailAuth());
        res.setRole(user.getRole());

        return ResponseUtils.generateDtoSuccess(new Information(MessageIdConst.I_LOGIN,
                messageSource.getMessage(MessageIdConst.I_LOGIN, null, LocaleAspect.LOCALE)), res);
    }
}
