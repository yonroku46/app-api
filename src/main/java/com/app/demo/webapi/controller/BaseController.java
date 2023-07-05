package com.app.demo.webapi.controller;

import com.app.demo.constants.SecurityConst;
import com.app.demo.dao.MUserDao;
import com.app.demo.dao.entity.MUser;
import com.app.demo.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * コントローラー共通部品
 *
 * @author y_ha
 */
public class BaseController {

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;

    @Autowired
    MUserDao mUserDao;

    public MUser loadMUser() {
        String authorization = request.getHeader(SecurityConst.REFRESH_TOKEN_HEADER);
        String token = authorization.replace(SecurityConst.TOKEN_PREFIX, "");
        Claims claims = JwtUtils.parseJWT(token);
        Object userIdObj = claims.get("userId");
        Object userMailObj = claims.get("mail");
        MUser entity = new MUser();
        if (userIdObj != null && userMailObj != null) {
            entity = mUserDao.findUserByPk(Integer.parseInt(userIdObj.toString()), userMailObj.toString());
        }
        return entity;
    }

    /**
     * ログインしているユーザー名
     *
     * @return
     */
    public String getCurrentUserName() {
        MUser entity = loadMUser();
        return entity.getUserName();
    }

    /**
     * ログインしているユーザーID
     *
     * @return
     */
    public Integer getCurrentUserId() {
        MUser entity = loadMUser();
        return entity.getUserId();
    }

    /**
     * ログインしているユーザーメール
     *
     * @return
     */
    public String getCurrentUserEmail() {
        MUser entity = loadMUser();
        return entity.getMail();
    }

    /**
     * ログインしているユーザーの権限
     *
     * @return
     */
    public Integer getCurrentUserRole() {
        MUser entity = loadMUser();
        return entity.getRole();
    }
}