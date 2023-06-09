package com.app.demo.dao;

import com.app.demo.aspect.LocaleAspect;
import com.app.demo.constants.MessageIdConst;
import com.app.demo.dao.entity.MUser;
import com.app.demo.dao.entity.MUserKey;
import com.app.demo.dao.mapper.MUserMapper;
import com.app.demo.dto.MenuAuthInfoDto;
import com.app.demo.exception.SystemException;
import com.app.demo.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
@Slf4j
public class MUserDao {

    @Autowired
    MessageSource messageSource;

    @Autowired
    private MUserMapper mUserMapper;

    /**
     * メールアドレスでユーザーの情報を取得
     *
     * @author y_ha
     */
    public MUser findUserByMail(String mail) {
        try {
            return mUserMapper.findUserByMail(mail);
        } catch (Exception exception) {
            final String methodName = "UserMapper#findUserByMail";
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("mail", mail);
            String overview = messageSource.getMessage(MessageIdConst.E_SQL_ISSUE, null, LocaleAspect.LOCALE);
            String detail = StringUtils.convertInterfaceErrorMsg(methodName, paramMap, exception);
            log.error(overview + detail);
            throw new SystemException(MessageIdConst.E_SQL_ISSUE, overview, detail);
        }
    }

    /**
     * ソーシャルログインユーザーの情報を取得
     *
     * @author y_ha
     */
    public MUser findSocialUser(String suid, String mail) {
        try {
            return mUserMapper.findSocialUser(suid, mail);
        } catch (Exception exception) {
            final String methodName = "UserMapper#findSocialUser";
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("suid", suid);
            paramMap.put("mail", mail);
            String overview = messageSource.getMessage(MessageIdConst.E_SQL_ISSUE, null, LocaleAspect.LOCALE);
            String detail = StringUtils.convertInterfaceErrorMsg(methodName, paramMap, exception);
            log.error(overview + detail);
            throw new SystemException(MessageIdConst.E_SQL_ISSUE, overview, detail);
        }
    }

    /**
     * PKでユーザーの情報を取得
     *
     * @author y_ha
     */
    public MUser findUserByPk(Integer userId, String mail) {
        try {
            MUserKey userKey = new MUserKey(userId, mail);
            return mUserMapper.findUser(userKey);
        } catch (Exception exception) {
            final String methodName = "UserMapper#findUserByPk";
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("userId", userId);
            paramMap.put("mail", mail);
            String overview = messageSource.getMessage(MessageIdConst.E_SQL_ISSUE, null, LocaleAspect.LOCALE);
            String detail = StringUtils.convertInterfaceErrorMsg(methodName, paramMap, exception);
            log.error(overview + detail);
            throw new SystemException(MessageIdConst.E_SQL_ISSUE, overview, detail);
        }
    }

    /**
     * メールとキーを所有してるユーザーを取得
     *
     * @author y_ha
     */
    public MUser findMailKeyUser(String mail, String mailKey) {
        try {
            return mUserMapper.findMailKeyUser(mail, mailKey);
        } catch (Exception exception) {
            final String methodName = "UserMapper#findMailKeyUser";
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("mail", mail);
            paramMap.put("mailKey", mailKey);
            String overview = messageSource.getMessage(MessageIdConst.E_SQL_ISSUE, null, LocaleAspect.LOCALE);
            String detail = StringUtils.convertInterfaceErrorMsg(methodName, paramMap, exception);
            log.error(overview + detail);
            throw new SystemException(MessageIdConst.E_SQL_ISSUE, overview, detail);
        }
    }

    /**
     * ユーザーの情報を登録
     *
     * @author y_ha
     */
    public int submit(MUser user) {
        try {
            return mUserMapper.insertSelective(user);
        } catch (Exception exception) {
            final String methodName = "UserMapper#submit";
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("user", user);
            String overview = messageSource.getMessage(MessageIdConst.E_SQL_ISSUE, null, LocaleAspect.LOCALE);
            String detail = StringUtils.convertInterfaceErrorMsg(methodName, paramMap, exception);
            log.error(overview + detail);
            throw new SystemException(MessageIdConst.E_SQL_ISSUE, overview, detail);
        }
    }

    /**
     * ユーザーの情報を更新
     *
     * @author y_ha
     */
    public int updateUserData(MUser user) {
        try {
            return mUserMapper.updateByPrimaryKey(user);
        } catch (Exception exception) {
            final String methodName = "UserMapper#updateUserData";
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("user", user);
            String overview = messageSource.getMessage(MessageIdConst.E_SQL_ISSUE, null, LocaleAspect.LOCALE);
            String detail = StringUtils.convertInterfaceErrorMsg(methodName, paramMap, exception);
            log.error(overview + detail);
            throw new SystemException(MessageIdConst.E_SQL_ISSUE, overview, detail);
        }
    }

    /**
     * ユーザーのアクセス権限情報を取得
     *
     * @author y_ha
     */
    public MenuAuthInfoDto getAccessibleInfo(MUserKey userKey, String path) {
        try {
            return mUserMapper.getAccessibleInfo(userKey, path);
        } catch (Exception exception) {
            final String methodName = "UserMapper#getAccessibleInfo";
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("userKey", userKey);
            paramMap.put("path", path);
            String overview = messageSource.getMessage(MessageIdConst.E_SQL_ISSUE, null, LocaleAspect.LOCALE);
            String detail = StringUtils.convertInterfaceErrorMsg(methodName, paramMap, exception);
            log.error(overview + detail);
            throw new SystemException(MessageIdConst.E_SQL_ISSUE, overview, detail);
        }
    }
}