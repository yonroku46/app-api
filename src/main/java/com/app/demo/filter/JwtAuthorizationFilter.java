package com.app.demo.filter;

import com.app.demo.aspect.LocaleAspect;
import com.app.demo.aspect.attribute.CheckToken;
import com.app.demo.aspect.attribute.LoginToken;
import com.app.demo.constants.SecurityConst;
import com.app.demo.dao.MUserDao;
import com.app.demo.dao.entity.MUser;
import com.app.demo.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * JWT認証処理クラス
 *
 * @author y_ha
 * @version 0.0.1
 */
@Component
public class JwtAuthorizationFilter implements HandlerInterceptor {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private MUserDao mUserDao;

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthorizationFilter.class);

    private static final List<String> PERMISSION_LIST = Arrays.asList("", "mypage");

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {

        // マッピングされていない場合は、直接に通過します。
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        // controller
        Class<?> clazz = handlerMethod.getBeanType();
        Method method = handlerMethod.getMethod();
        if (method.isAnnotationPresent(LoginToken.class)) {
            LoginToken loginToken = method.getAnnotation(LoginToken.class);
            if (loginToken.required()) {
                return true;
            }
        }
        boolean isNeedVerify = false;
        //ユーザー権限が必要なコメントがあるか確認します。
        if (clazz.isAnnotationPresent(CheckToken.class)) {
            CheckToken checkCotrollerToken = clazz.getAnnotation(CheckToken.class);
            if (checkCotrollerToken.required()) {
                isNeedVerify = true;
            }
        }
        if (method.isAnnotationPresent(CheckToken.class)) {
            CheckToken checkMethodToken = method.getAnnotation(CheckToken.class);
            if (checkMethodToken.required()) {
                isNeedVerify = true;
            }
        }

        if (isNeedVerify) {
            return verifyToken(httpServletRequest, httpServletResponse);
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    private boolean verifyToken(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        // リクエストヘッダからtokenを取得する
        String authorization = httpServletRequest.getHeader(SecurityConst.TOKEN_HEADER);
        if (authorization == null) {
            String message = messageSource.getMessage("error.noAccessToken", null, LocaleAspect.LOCALE);
            httpServletResponse.sendError((HttpServletResponse.SC_UNAUTHORIZED), message);
            return false;
        }
        String token = authorization.replace(SecurityConst.TOKEN_PREFIX, "");
        if (token == null) {
            String message = messageSource.getMessage("error.noAccessToken", null, LocaleAspect.LOCALE);
            httpServletResponse.sendError((HttpServletResponse.SC_UNAUTHORIZED), message);
            return false;
        }

        try {
            Claims claims = JwtUtils.parseJWT(token);
            // ログインしていたユーザーのアクセストークンがDBに保存されているものと一致しているかどうかを判断する。
            MUser userForBase = this.getAuthentication(claims);
            if (userForBase != null) {
                return true;
            } else {
                return false;
            }
            // リクエストされた画面に権限がない場合権限エラーを出力する。
//            MenuAuthInfoDto accessibleInfo = this.getUserAccessibleInfo(userForBase, httpServletRequest.getHeader("Mapping-Path"));
//            if (accessibleInfo == null || !accessibleInfo.getAccessibleFlg()) {
//                httpServletResponse.sendError((HttpServletResponse.SC_FORBIDDEN));
//                return false;
//            } else {
//                return true;
//            }
        } catch (ExpiredJwtException exception) {
            String message = messageSource.getMessage("error.expiredJWT", new String[]{token, exception.getMessage()}, LocaleAspect.LOCALE);
            logger.info(message);
            httpServletResponse.sendError((HttpServletResponse.SC_UNAUTHORIZED), exception.getMessage());
        } catch (UnsupportedJwtException exception) {
            String message = messageSource.getMessage("error.unsupportedJWT", new String[]{token, exception.getMessage()}, LocaleAspect.LOCALE);
            logger.info(message);
            httpServletResponse.sendError((HttpServletResponse.SC_UNAUTHORIZED), exception.getMessage());
        } catch (MalformedJwtException exception) {
            String message = messageSource.getMessage("error.invalidJWT", new String[]{token, exception.getMessage()}, LocaleAspect.LOCALE);
            logger.info(message);
            httpServletResponse.sendError((HttpServletResponse.SC_UNAUTHORIZED), exception.getMessage());
        } catch (IllegalArgumentException exception) {
            String message = messageSource.getMessage("error.illegalJWT", new String[]{token, exception.getMessage()}, LocaleAspect.LOCALE);
            logger.info(message, token, exception.getMessage());
            httpServletResponse.sendError((HttpServletResponse.SC_UNAUTHORIZED), exception.getMessage());
        }
        return false;
    }

    private MUser getAuthentication(Claims claims) {
        String userId = claims.get("userId", String.class);
        String userMail = claims.get("userMail", String.class);
        MUser userForBase = mUserDao.findUserById(userId, userMail);
        return userForBase;
    }

//    private MenuAuthInfoDto getUserAccessibleInfo(MUser user, String path) {
//        MenuAuthInfoDto accessibleInfo = new MenuAuthInfoDto();
//        // 権限確認の必要がないパスはアクセス許可を設定
//        if (PERMISSION_LIST.contains(path)) {
//            accessibleInfo.setRequestMapping(path);
//            accessibleInfo.setAccessibleFlg(Boolean.TRUE);
//        } else {
//            accessibleInfo = mUserDao.getAccessibleInfo(user.getUserId(), user.getUserMail(), path);
//        }
//        return accessibleInfo;
//    }
}