package com.app.demo.webapi.service.impl;

import com.app.demo.aspect.LocaleAspect;
import com.app.demo.constants.MessageIdConst;
import com.app.demo.dao.MUserDao;
import com.app.demo.dao.entity.MUser;
import com.app.demo.dto.response.UserInfoResDto;
import com.app.demo.dto.response.core.Information;
import com.app.demo.dto.response.core.ResponseDto;
import com.app.demo.utils.ResponseUtils;
import com.app.demo.webapi.service.MUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

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
    public ResponseDto login(String userMail, String userPw) {
        UserInfoResDto res = new UserInfoResDto();
        MUser user  = mUserDao.login(userMail, userPw);
        if (user != null) {
            res.setUserMail(user.getUserMail());
            res.setUserName(user.getUserName());
        }
        return ResponseUtils.generateDtoSuccess(new Information(MessageIdConst.I_GETTING_SUCCESS,
                messageSource.getMessage(MessageIdConst.I_GETTING_SUCCESS, null, LocaleAspect.LOCALE)), res);
    }

    @Override
    public ResponseDto findUserById(String userId, String userMail) {
        UserInfoResDto res = new UserInfoResDto();
        MUser user  = mUserDao.findUserById(userId, userMail);
        if (user != null) {
            res.setUserMail(user.getUserMail());
            res.setUserName(user.getUserName());
        }
        return ResponseUtils.generateDtoSuccess(new Information(MessageIdConst.I_GETTING_SUCCESS,
                messageSource.getMessage(MessageIdConst.I_GETTING_SUCCESS, null, LocaleAspect.LOCALE)), res);
    }
}
