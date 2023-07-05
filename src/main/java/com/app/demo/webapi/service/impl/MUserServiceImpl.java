package com.app.demo.webapi.service.impl;

import com.app.demo.aspect.LocaleAspect;
import com.app.demo.constants.MessageIdConst;
import com.app.demo.constants.SecurityConst;
import com.app.demo.dao.MUserDao;
import com.app.demo.dao.entity.MUser;
import com.app.demo.dto.request.KeyCheckReqDto;
import com.app.demo.dto.request.LoginReqDto;
import com.app.demo.dto.request.RecoverReqDto;
import com.app.demo.dto.request.SubmitReqDto;
import com.app.demo.dto.response.FlgResDto;
import com.app.demo.dto.response.KeyCheckResDto;
import com.app.demo.dto.response.UserInfoResDto;
import com.app.demo.dto.response.core.Information;
import com.app.demo.dto.response.core.ResponseDto;
import com.app.demo.exception.ApplicationException;
import com.app.demo.utils.DateUtils;
import com.app.demo.utils.JwtUtils;
import com.app.demo.utils.PasswordUtils;
import com.app.demo.utils.ResponseUtils;
import com.app.demo.webapi.service.MUserService;
import com.app.demo.webapi.service.MailService;
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
 */
@Service
@Slf4j
public class MUserServiceImpl implements MUserService {

    @Autowired
    MessageSource messageSource;

    @Autowired
    private MailService mailService;

    @Autowired
    private MUserDao mUserDao;

    @Override
    public ResponseDto login(LoginReqDto reqDto) {
        String token = "";
        String refrehToken = "";

        UserInfoResDto res = new UserInfoResDto();
        MUser user = mUserDao.findUserByMail(reqDto.getMail());
        if (user != null) {
            String encyptPsw = PasswordUtils.encode(reqDto.getPassword());
            if (!user.getPassword().equals(encyptPsw)) {
                String message = messageSource.getMessage("login.password.incorrect", null, LocaleAspect.LOCALE);
                log.warn(message);
                throw new ApplicationException(HttpStatus.OK, null, message);
            } else {
                // ログイン成功の場合ユーザマスタの最終ログイン日時を更新する
                MUser record = mUserDao.findUserByPk(user.getUserId(), user.getMail());
                // UTCで日時を取得
                LocalDateTime latestLogin = DateUtils.getUTCdatetimeAsDate();
                record.setLatestLogin(Date.from(latestLogin.atZone(ZoneId.systemDefault()).toInstant()));
                mUserDao.updateUserData(record);

                token = JwtUtils.createJWT(SecurityConst.EXPIRATION_TIME, user.getUserId(), user.getUserName(), user.getMail(), user.getRole());
                refrehToken = JwtUtils.createJWT(SecurityConst.REFRESH_EXPIRATION_TIME, user.getUserId(), user.getUserName(), user.getMail(), user.getRole());

                res.setUserId(user.getUserId());
                res.setUserName(user.getUserName());
                res.setProfileImg(user.getProfileImg());
                res.setMail(user.getMail());
                res.setToken(token);
                res.setRefreshToken(refrehToken);
                res.setMailAuth(user.getMailAuth());
                res.setRole(user.getRole());
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
    public ResponseDto logout(Integer userId, String mail) {
        UserInfoResDto res = null;
        return ResponseUtils.generateDtoSuccess(new Information(MessageIdConst.I_LOGOUT,
                messageSource.getMessage(MessageIdConst.I_LOGOUT, null, LocaleAspect.LOCALE)), res);
    }

    @Override
    public ResponseDto submit(SubmitReqDto reqDto) {
        FlgResDto res = new FlgResDto();
        MUser user = mUserDao.findUserByMail(reqDto.getMail());
        if (user == null) {
            String mail = reqDto.getMail();
            String mailKey = PasswordUtils.generateRandomKey(20);
            String encryptPsw = PasswordUtils.encode(reqDto.getPassword());
            // ユーザー登録
            user = new MUser();
            user.setPassword(encryptPsw);
            user.setUserName(reqDto.getName());
            user.setMail(mail);
            user.setMailKey(mailKey);
            mUserDao.submit(user);
            // 認証メール送信
            mailService.sendAuthMail(mail, mailKey, SecurityConst.SUBMIT_AUTH_PATH);
            res.setIsFlg(Boolean.TRUE);
        } else {
            String message = messageSource.getMessage("info.alreadyExist", null, LocaleAspect.LOCALE);
            log.warn(message);
            throw new ApplicationException(HttpStatus.OK, null, message);
        }
        return ResponseUtils.generateDtoSuccess(new Information(MessageIdConst.I_SAVE_SUCCESS,
                messageSource.getMessage(MessageIdConst.I_SAVE_SUCCESS, null, LocaleAspect.LOCALE)), res);
    }

    @Override
    public ResponseDto recoverMail(RecoverReqDto reqDto) {
        FlgResDto res = new FlgResDto();
        MUser user = mUserDao.findUserByMail(reqDto.getMail());
        if (user != null) {
            String mail = reqDto.getMail();
            String mailKey = PasswordUtils.generateRandomKey(20);
            // ユーザー認証キー登録
            user.setMailKey(mailKey);
            mUserDao.updateUserData(user);
            // 認証メール送信
            mailService.sendAuthMail(mail, mailKey, SecurityConst.RECOVER_AUTH_PATH);
            res.setIsFlg(Boolean.TRUE);
        } else {
            String message = messageSource.getMessage(MessageIdConst.E_DATA_NOT_FOUND, null, LocaleAspect.LOCALE);
            log.warn(message);
            throw new ApplicationException(HttpStatus.OK, null, message);
        }
        return ResponseUtils.generateDtoSuccess(new Information(MessageIdConst.I_UPDATE_SUCCESS,
                messageSource.getMessage(MessageIdConst.I_UPDATE_SUCCESS, null, LocaleAspect.LOCALE)), res);
    }

    @Override
    public ResponseDto recover(RecoverReqDto reqDto) {
        FlgResDto res = new FlgResDto();
        MUser user = mUserDao.findUserByMail(reqDto.getMail());
        if (user != null) {
            String encryptPsw = PasswordUtils.encode(reqDto.getPassword());
            // ユーザーパスワード更新
            user.setPassword(encryptPsw);
            mUserDao.updateUserData(user);
            res.setIsFlg(Boolean.TRUE);
        } else {
            String message = messageSource.getMessage(MessageIdConst.E_DATA_NOT_FOUND, null, LocaleAspect.LOCALE);
            log.warn(message);
            throw new ApplicationException(HttpStatus.OK, null, message);
        }
        return ResponseUtils.generateDtoSuccess(new Information(MessageIdConst.I_UPDATE_SUCCESS,
                messageSource.getMessage(MessageIdConst.I_UPDATE_SUCCESS, null, LocaleAspect.LOCALE)), res);
    }

    @Override
    public ResponseDto keyCheck(KeyCheckReqDto req) {
        KeyCheckResDto res = new KeyCheckResDto();
        String mail = PasswordUtils.decode(req.getMail());
        String mailKey = req.getMailKey();
        MUser user = mUserDao.findMailKeyUser(mail, mailKey);
        if (user != null) {
            // ユーザーメールフラグ更新
            user.setMailKey(null);
            user.setMailAuth(Boolean.TRUE);
            mUserDao.updateUserData(user);
            res.setMail(mail);
        } else {
            String message = messageSource.getMessage(MessageIdConst.E_DATA_NOT_FOUND, null, LocaleAspect.LOCALE);
            log.warn(message);
            throw new ApplicationException(HttpStatus.OK, null, message);
        }
        return ResponseUtils.generateDtoSuccess(new Information(MessageIdConst.I_LOGIN,
                messageSource.getMessage(MessageIdConst.I_UPDATE_SUCCESS, null, LocaleAspect.LOCALE)), res);
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
        try {
            Claims claims = JwtUtils.parseJWT(refreshToken);
            Integer userId = claims.get("userId", Integer.class);
            String mail = claims.get("mail", String.class);
            MUser user  = mUserDao.findUserByPk(userId, mail);
            if (user != null) {
                String newToken = JwtUtils.createJWT(SecurityConst.EXPIRATION_TIME, user.getUserId(), user.getUserName(), user.getMail(), user.getRole());
                String newRefreshToken = JwtUtils.createJWT(SecurityConst.REFRESH_EXPIRATION_TIME, user.getUserId(), user.getUserName(), user.getMail(), user.getRole());

                UserInfoResDto res = new UserInfoResDto();
                res.setUserId(user.getUserId());
                res.setUserName(user.getUserName());
                res.setProfileImg(user.getProfileImg());
                res.setMail(user.getMail());
                res.setToken(newToken);
                res.setRefreshToken(newRefreshToken);
                res.setMailAuth(user.getMailAuth());
                res.setRole(user.getRole());

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
    public ResponseDto findUser(Integer userId, String mail) {
        UserInfoResDto res = new UserInfoResDto();
        MUser user  = mUserDao.findUserByPk(userId, mail);
        if (user != null) {
            res.setUserId(user.getUserId());
            res.setMail(user.getMail());
            res.setUserName(user.getUserName());
        }
        return ResponseUtils.generateDtoSuccess(new Information(MessageIdConst.I_GETTING_SUCCESS,
                messageSource.getMessage(MessageIdConst.I_GETTING_SUCCESS, null, LocaleAspect.LOCALE)), res);
    }
}
