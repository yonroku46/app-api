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
import com.app.demo.webapi.service.OAuth2Service;
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
public class OAuth2ServiceImpl implements OAuth2Service {

    private final MessageSource messageSource;

    private final OAuth2Utils oauth2Utils;

    private final MUserDao mUserDao;

    @Override
    public ResponseDto getGoogleAccessToken(String code) throws JsonProcessingException {
        // ユーザーインスタンス
        UserInfoResDto res = new UserInfoResDto();

        // IDトークン取得
        Map<String, Object> tokenMap = oauth2Utils.getGoogleToken(code);
        String accessToken = tokenMap.get("access_token").toString();

        // プロフィール取得
        Map<String, Object> profileMap = oauth2Utils.getGoogleProfile(accessToken);
        String suid = profileMap.get("id").toString();
        String mail = profileMap.get("email").toString();

        MUser record = mUserDao.findSocialUser(suid, mail);
        // 新規の場合は先に登録処理
        if (record == null) {
            record = new MUser();
            record.setSuid(suid);
            record.setUserName(profileMap.get("name").toString());
            record.setProfileImg(profileMap.get("picture").toString());
            record.setMail(mail);
            record.setMailAuth(Boolean.TRUE);
            mUserDao.submit(record);
        }

        // ログイン処理
        MUser user = mUserDao.findUserByMail(mail);
        // ログイン日時とプロフィール更新
        LocalDateTime latestLogin = DateUtils.getUTCdatetimeAsDate();
        user.setLatestLogin(Date.from(latestLogin.atZone(ZoneId.systemDefault()).toInstant()));
        // 既に会員登録済みのユーザーはSUIDがNULLのため、設定することで連動
        if (record.getSuid().isEmpty()) {
            user.setSuid(suid);
        }
        mUserDao.updateUserData(user);

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
        String mail = profileMap.get("email").toString();

        MUser record = mUserDao.findSocialUser(suid, mail);
        // 新規の場合は先に登録処理
        if (record == null) {
            record = new MUser();
            record.setSuid(suid);
            record.setUserName(profileMap.get("name").toString());
            record.setProfileImg(profileMap.get("picture").toString());
            record.setMail(mail);
            record.setMailAuth(Boolean.TRUE);
            mUserDao.submit(record);
        }

        // ログイン処理
        MUser user = mUserDao.findUserByMail(mail);
        // ログイン日時とプロフィール更新
        LocalDateTime latestLogin = DateUtils.getUTCdatetimeAsDate();
        user.setLatestLogin(Date.from(latestLogin.atZone(ZoneId.systemDefault()).toInstant()));
        // 既に会員登録済みのユーザーはSUIDがNULLのため、設定することで連動
        if (record.getSuid().isEmpty()) {
            user.setSuid(suid);
        }
        mUserDao.updateUserData(user);

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
