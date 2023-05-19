package com.app.demo.webapi.service.impl;

import com.app.demo.aspect.LocaleAspect;
import com.app.demo.constants.MessageIdConst;
import com.app.demo.constants.SecurityConst;
import com.app.demo.dao.MUserDao;
import com.app.demo.dao.entity.MUser;
import com.app.demo.dto.request.LoginReqDto;
import com.app.demo.dto.response.UserInfoResDto;
import com.app.demo.dto.response.core.Information;
import com.app.demo.dto.response.core.ResponseDto;
import com.app.demo.exception.ApplicationException;
import com.app.demo.utils.DateUtils;
import com.app.demo.utils.JwtUtils;
import com.app.demo.utils.PasswordUtils;
import com.app.demo.utils.ResponseUtils;
import com.app.demo.webapi.service.MUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
                MUser record = mUserDao.findUserByPk(user.getUid(), user.getMail());
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
        return ResponseUtils.generateDtoSuccess(new Information(MessageIdConst.I_GETTING_SUCCESS,
                messageSource.getMessage(MessageIdConst.I_GETTING_SUCCESS, null, LocaleAspect.LOCALE)), res);
    }

    @Override
    public ResponseDto loginOut(Integer uid, String mail) {
        UserInfoResDto res = null;
        MUser record = mUserDao.findUserByPk(uid, mail);
        record.setToken("");
        mUserDao.updateUserData(record);
        return ResponseUtils.generateDtoSuccess(
                new Information("info.loginOut", messageSource.getMessage("info.loginOut", null, LocaleAspect.LOCALE)), res);
    }

    @Override
    public ResponseDto findUserByPk(Integer uid, String mail) {
        UserInfoResDto res = new UserInfoResDto();
        MUser user  = mUserDao.findUserByPk(uid, mail);
        if (user != null) {
            res.setMail(user.getMail());
            res.setUserName(user.getUserName());
        }
        return ResponseUtils.generateDtoSuccess(new Information(MessageIdConst.I_GETTING_SUCCESS,
                messageSource.getMessage(MessageIdConst.I_GETTING_SUCCESS, null, LocaleAspect.LOCALE)), res);
    }

    @Override
    public int updateUserAccessToken(Integer uid, String mail, String accessToken) {
        MUser record = mUserDao.findUserByPk(uid, mail);
        record.setToken(accessToken);
        return mUserDao.updateUserData(record);
    }
}
