package com.app.demo.webapi.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.app.demo.aspect.LocaleAspect;
import com.app.demo.constants.MessageIdConst;
import com.app.demo.constants.SecurityConst;
import com.app.demo.dao.MUserDao;
import com.app.demo.dao.entity.MUser;
import com.app.demo.dto.request.*;
import com.app.demo.dto.response.FlgResDto;
import com.app.demo.dto.response.KeyCheckResDto;
import com.app.demo.dto.response.UserInfoResDto;
import com.app.demo.dto.response.core.Information;
import com.app.demo.dto.response.core.ResponseDto;
import com.app.demo.exception.ApplicationException;
import com.app.demo.exception.SystemException;
import com.app.demo.utils.*;
import com.app.demo.webapi.service.UserService;
import com.app.demo.webapi.service.MailService;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * ユーザー機能サービス詳細
 *
 * @author y_ha
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    MessageSource messageSource;

    @Autowired
    private MailService mailService;

    @Autowired
    private MUserDao mUserDao;

    /**
     * S3 バケット名
     */
    @Value("${cloud.aws.s3.bucketName}")
    private String AWS_S3_BUCKET_NAME;

    /**
     * 処理が正常終了した際のプリフィックス名
     */
    @Value("${cloud.aws.s3.prefix.user}")
    private String AWS_S3_PREFIX_USER;

    @Autowired
    AmazonS3 amazonS3;

    @Override
    public ResponseDto login(LoginReqDto reqDto) {
        // ユーザーインスタンス
        UserInfoResDto res = new UserInfoResDto();

        MUser user = mUserDao.findUserByMail(reqDto.getMail());
        if (user != null) {
            String encyptPsw = PasswordUtils.encode(reqDto.getPassword());
            if (user.getPassword() == null || !user.getPassword().equals(encyptPsw)) {
                String message = messageSource.getMessage("login.password.incorrect", null, LocaleAspect.LOCALE);
                log.warn(message);
                throw new ApplicationException(HttpStatus.OK, null, message);
            } else {
                // ログイン成功の場合ユーザマスタの最終ログイン日時を更新
                LocalDateTime latestLogin = DateUtils.getUTCdatetimeAsDate();
                user.setLatestLogin(Date.from(latestLogin.atZone(ZoneId.systemDefault()).toInstant()));
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
        return ResponseUtils.generateDtoSuccess(new Information(MessageIdConst.I_LOGOUT,
                messageSource.getMessage(MessageIdConst.I_LOGOUT, null, LocaleAspect.LOCALE)), null);
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
                messageSource.getMessage(MessageIdConst.I_SAVE_SUCCESS, new String[]{"UserInfo"}, LocaleAspect.LOCALE)), res);
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
                messageSource.getMessage(MessageIdConst.I_UPDATE_SUCCESS, new String[]{"UserMailKey"}, LocaleAspect.LOCALE)), res);
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
                messageSource.getMessage(MessageIdConst.I_UPDATE_SUCCESS, new String[]{"UserPassword"}, LocaleAspect.LOCALE)), res);
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
                messageSource.getMessage(MessageIdConst.I_UPDATE_SUCCESS, new String[]{"UserMailAuth"}, LocaleAspect.LOCALE)), res);
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
    public ResponseDto getUserInfo(Integer userId, String mail) {
        UserInfoResDto res = new UserInfoResDto();
        MUser user = mUserDao.findUserByPk(userId, mail);
        if (user != null) {
            res.setUserId(user.getUserId());
            res.setMail(user.getMail());
            res.setUserName(user.getUserName());
        }
        return ResponseUtils.generateDtoSuccess(new Information(MessageIdConst.I_GETTING_SUCCESS,
                messageSource.getMessage(MessageIdConst.I_GETTING_SUCCESS, new String[]{"UserInfo"}, LocaleAspect.LOCALE)), res);
    }

    @Override
    public ResponseDto updateUserInfo(Integer userId, String mail,  MultipartFile profileImg) {
        UserInfoResDto res = new UserInfoResDto();
        MUser user = mUserDao.findUserByPk(userId, mail);
        if (user != null) {
            // ファイルをs3に保存する
            try {
                String profilePrefix = userId + "/profile/";
                // ファイルのコンテンツタイプを取得する
                String fileType = profileImg.getOriginalFilename().substring(profileImg.getOriginalFilename().lastIndexOf("."));
                // サーバー保存用にファイル名をリネームする
                String saveFileName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()) + fileType;

                // アップロードしてユーザー情報更新
                String profileSaveName = profilePrefix + saveFileName;
                String filePath = S3Utils.uploadFile(profileSaveName, profileImg.getInputStream(), AWS_S3_BUCKET_NAME, AWS_S3_PREFIX_USER, amazonS3);
                user.setProfileImg(filePath);
                mUserDao.updateUserData(user);

                // 前のユーザープロフィール削除
                List<String> userFileList = S3Utils.getFileList(AWS_S3_BUCKET_NAME, AWS_S3_PREFIX_USER + "/" + profilePrefix, amazonS3);
                for (String fileName : userFileList) {
                    if (!fileName.equals(filePath)) {
                        S3Utils.deleteFile(AWS_S3_BUCKET_NAME, fileName, amazonS3);
                    }
                }

                String token = JwtUtils.createJWT(SecurityConst.EXPIRATION_TIME, user.getUserId(), user.getUserName(), user.getMail(), user.getRole());
                String refreshToken = JwtUtils.createJWT(SecurityConst.REFRESH_EXPIRATION_TIME, user.getUserId(), user.getUserName(), user.getMail(), user.getRole());

                //更新されたユーザー情報セット
                MUser updatedUser = mUserDao.findUserByPk(userId, mail);
                res.setUserId(updatedUser.getUserId());
                res.setUserName(updatedUser.getUserName());
                res.setProfileImg(updatedUser.getProfileImg());
                res.setMail(updatedUser.getMail());
                res.setToken(token);
                res.setRefreshToken(refreshToken);
                res.setMailAuth(updatedUser.getMailAuth());
                res.setRole(updatedUser.getRole());
            } catch (IOException exception) {
                String message = messageSource.getMessage("error.system", null, LocaleAspect.LOCALE);
                String errorDetail = StringUtils.convertStackTraceToString(exception);
                log.error(errorDetail);
                throw new SystemException("error.file", message, errorDetail);
            }
        }
        return ResponseUtils.generateDtoSuccess(new Information(MessageIdConst.I_UPDATE_SUCCESS,
                messageSource.getMessage(MessageIdConst.I_UPDATE_SUCCESS, new String[]{"UserInfo"}, LocaleAspect.LOCALE)), res);
    }
}
