package com.app.demo.webapi.controller;

import com.app.demo.constants.SecurityConst;
import com.app.demo.dao.entity.MUser;
import com.app.demo.dao.mapper.MUserMapper;
import com.app.demo.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * コントローラー共通部品
 *
 * @author y_ha
 * @version 0.0.1
 */
public class BaseController {

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;

    @Autowired
    MUserMapper mUserMapper;

    public MUser loadMUser() {
        String authorization = request.getHeader(SecurityConst.REFRESH_TOKEN_HEADER);
        String token = authorization.replace(SecurityConst.TOKEN_PREFIX, "");
        Claims claims = JwtUtils.parseJWT(token);
        Object userIdObj = claims.get("userId");
        Object userMailObj = claims.get("userMail");
        MUser entity = new MUser();
        if (userIdObj != null && userMailObj != null) {
            entity = mUserMapper.findUserById(userIdObj.toString(), userMailObj.toString());
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
    public String getCurrentUserId() {
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
        return entity.getUserMail();
    }

    /**
     * ログインしているユーザーの法人フラグ
     *
     * @return
     */
    public String getCurrentUserCorpFlg() {
        String authorization = request.getHeader(SecurityConst.REFRESH_TOKEN_HEADER);
        String token = authorization.replace(SecurityConst.TOKEN_PREFIX, "");
        Claims claims = JwtUtils.parseJWT(token);
        Object corpFlgObj = claims.get("corpFlg");
        if (corpFlgObj != null) {
            return corpFlgObj.toString();
        } else {
            return null;
        }
    }
}