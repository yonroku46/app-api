package com.app.demo.webapi.service.impl;

import com.app.demo.aspect.LocaleAspect;
import com.app.demo.constants.MessageIdConst;
import com.app.demo.constants.SecurityConst;
import com.app.demo.dao.MUserDao;
import com.app.demo.dao.entity.MUser;
import com.app.demo.dto.request.LoginReqDto;
import com.app.demo.dto.request.SubmitReqDto;
import com.app.demo.dto.response.UserInfoResDto;
import com.app.demo.dto.response.core.Information;
import com.app.demo.dto.response.core.ResponseDto;
import com.app.demo.exception.ApplicationException;
import com.app.demo.utils.DateUtils;
import com.app.demo.utils.JwtUtils;
import com.app.demo.utils.PasswordUtils;
import com.app.demo.utils.ResponseUtils;
import com.app.demo.webapi.service.MUserService;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * ユーザー機能サービス詳細
 *
 * @author y_ha
 * @version 0.0.1
 */
@Service
@Slf4j
public class MUserServiceImpl implements MUserService {

    @Autowired
    MessageSource messageSource;

    @Autowired
    private MUserDao mUserDao;

    @Override
    public ResponseDto login(LoginReqDto reqDto) {
        String token = "";
        String refrehToken = "";

        UserInfoResDto res = new UserInfoResDto();
        MUser user = mUserDao.login(reqDto.getMail());
        if (user != null) {
            String encyptPsw = PasswordUtils.encode(reqDto.getPassword());
            if (!user.getPassword().equals(encyptPsw)) {
                String message = messageSource.getMessage("login.password.incorrect", null, LocaleAspect.LOCALE);
                log.warn(message);
                throw new ApplicationException(HttpStatus.OK, null, message);
            } else {
                // ログイン成功の場合ユーザマスタの最終ログイン日時を更新する
                MUser record = mUserDao.findUser(user.getUid(), user.getMail());
                // UTCで日時を取得
                LocalDateTime latestLogin = DateUtils.getUTCdatetimeAsDate();
                record.setLatestLogin(Date.from(latestLogin.atZone(ZoneId.systemDefault()).toInstant()));
                mUserDao.updateUserData(record);

                token = JwtUtils.createJWT(SecurityConst.EXPIRATION_TIME, user.getUid(), user.getUserName(), user.getMail(), user.getCorpFlg());
                refrehToken = JwtUtils.createJWT(SecurityConst.REFRESH_EXPIRATION_TIME, user.getUid(), user.getUserName(), user.getMail(), user.getCorpFlg());
                this.updateUserAccessToken(user.getUid(), user.getMail(), token);

                res.setUid(user.getUid());
                res.setUserName(user.getUserName());
                res.setMail(user.getMail());
                res.setToken(token);
                res.setRefreshToken(refrehToken);
            }
        } else {
            String message = messageSource.getMessage("login.user.notExist", null, LocaleAspect.LOCALE);
            log.warn(message);
            throw new ApplicationException(HttpStatus.OK, null, message);
        }
        return ResponseUtils.generateDtoSuccess(new Information(MessageIdConst.I_LOGIN,
                messageSource.getMessage(MessageIdConst.I_LOGIN, null, LocaleAspect.LOCALE)), res);
    }

    @Override
    public ResponseDto loginOut(Integer uid, String mail) {
        UserInfoResDto res = null;
        MUser record = mUserDao.findUser(uid, mail);
        record.setToken("");
        mUserDao.updateUserData(record);
        return ResponseUtils.generateDtoSuccess(new Information(MessageIdConst.I_LOGOUT,
                messageSource.getMessage(MessageIdConst.I_LOGOUT, null, LocaleAspect.LOCALE)), res);
    }

    @Override
    public ResponseDto submit(SubmitReqDto reqDto) {
        UserInfoResDto res = new UserInfoResDto();
        MUser user = mUserDao.login(reqDto.getMail());
        if (user == null) {
            user = new MUser();
            String encyptPsw = PasswordUtils.encode(reqDto.getPassword());
            user.setPassword(encyptPsw);
            user.setUserName(reqDto.getName());
            user.setMail(reqDto.getMail());
            mUserDao.submit(user);
        } else {
            String message = messageSource.getMessage("info.alreadyExist", null, LocaleAspect.LOCALE);
            log.warn(message);
            throw new ApplicationException(HttpStatus.OK, null, message);
        }
        return ResponseUtils.generateDtoSuccess(new Information(MessageIdConst.I_LOGIN,
                messageSource.getMessage(MessageIdConst.I_SAVE_SUCCESS, null, LocaleAspect.LOCALE)), res);
    }

    @Override
    @Transactional
    public ResponseDto refreshToken(HttpServletRequest httpServletRequest) {
        String refreshTokenHeader = httpServletRequest.getHeader(SecurityConst.REFRESH_TOKEN_HEADER);
        String refreshToken = refreshTokenHeader.replace(SecurityConst.TOKEN_PREFIX, "");
        if (refreshToken == null) {
            String message = messageSource.getMessage("error.noAccessToken", null, LocaleAspect.LOCALE);
            throw new ApplicationException(HttpStatus.OK, "error.noAccessToken", message);
        }
        String authorization = httpServletRequest.getHeader(SecurityConst.TOKEN_HEADER);
        String accessToken = authorization.replace(SecurityConst.TOKEN_PREFIX, "");
        try {
            Claims claims = JwtUtils.parseJWT(refreshToken);
            Integer uid = claims.get("uid", Integer.class);
            String mail = claims.get("mail", String.class);
            MUser user  = mUserDao.findUser(uid, mail);
            if (user.getToken().equals(accessToken)) {
                String newToken = JwtUtils.createJWT(SecurityConst.EXPIRATION_TIME, user.getUid(), user.getUserName(), user.getMail(), user.getCorpFlg());
                String newRefreshToken = JwtUtils.createJWT(SecurityConst.REFRESH_EXPIRATION_TIME, user.getUid(), user.getUserName(), user.getMail(), user.getCorpFlg());
                this.updateUserAccessToken(user.getUid(), user.getMail(), newToken);

                UserInfoResDto res = new UserInfoResDto();
                res.setUid(user.getUid());
                res.setMail(user.getMail());
                res.setUserName(user.getUserName());
                res.setToken(newToken);
                res.setRefreshToken(newRefreshToken);

                return ResponseUtils.generateDtoSuccess(
                        new Information("info.refreshToken", messageSource.getMessage("info.refreshToken", new String[]{}, LocaleAspect.LOCALE)), res);
            } else {
                String message = messageSource.getMessage("error.unsupportedJWT", new String[]{}, LocaleAspect.LOCALE);
                throw new ApplicationException(HttpStatus.OK, "error.unsupportedJWT", message);
            }
        } catch (Exception exception) {
            String message = messageSource.getMessage("error.expiredJWT", new String[]{refreshToken, exception.getMessage()}, LocaleAspect.LOCALE);
            throw new ApplicationException(HttpStatus.OK, "error.expiredJWT", message);
        }
    }

    @Override
    public ResponseDto findUser(Integer uid, String mail) {
        UserInfoResDto res = new UserInfoResDto();
        MUser user  = mUserDao.findUser(uid, mail);
        if (user != null) {
            res.setUid(user.getUid());
            res.setMail(user.getMail());
            res.setUserName(user.getUserName());
            res.setToken(user.getToken());
            res.setRefreshToken(user.getRefreshToken());
        }
        return ResponseUtils.generateDtoSuccess(new Information(MessageIdConst.I_GETTING_SUCCESS,
                messageSource.getMessage(MessageIdConst.I_GETTING_SUCCESS, null, LocaleAspect.LOCALE)), res);
    }

    @Override
    public int updateUserAccessToken(Integer uid, String mail, String accessToken) {
        MUser record = mUserDao.findUser(uid, mail);
        record.setToken(accessToken);
        return mUserDao.updateUserData(record);
    }
}
