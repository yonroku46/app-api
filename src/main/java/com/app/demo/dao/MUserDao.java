package com.app.demo.dao;

import com.app.demo.aspect.LocaleAspect;
import com.app.demo.constants.MessageIdConst;
import com.app.demo.dao.entity.MUser;
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
     * ユーザー情報を取得
     *
     * @author y_ha
     * @version 0.0.1
     */
    public MUser findUserById(String userId, String userMail) {
        try {
            return mUserMapper.findUserById(userId, userMail);
        } catch (Exception exception) {
            final String methodName = "UserMapper#findUserById";
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("userId", userId);
            paramMap.put("userMail", userMail);
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
     * @version 0.0.1
     */
    public MenuAuthInfoDto getAccessibleInfo(String userId, String userMail, String path) {
        try {
            return mUserMapper.getAccessibleInfo(userId, userMail, path);
        } catch (Exception exception) {
            final String methodName = "UserMapper#getAccessibleInfo";
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("userId", userId);
            paramMap.put("userMail", userMail);
            paramMap.put("path", path);
            String overview = messageSource.getMessage(MessageIdConst.E_SQL_ISSUE, null, LocaleAspect.LOCALE);
            String detail = StringUtils.convertInterfaceErrorMsg(methodName, paramMap, exception);
            log.error(overview + detail);
            throw new SystemException(MessageIdConst.E_SQL_ISSUE, overview, detail);
        }
    }
}
