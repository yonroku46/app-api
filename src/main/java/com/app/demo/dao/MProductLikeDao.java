package com.app.demo.dao;

import com.app.demo.aspect.LocaleAspect;
import com.app.demo.constants.MessageIdConst;
import com.app.demo.dao.entity.MProductLike;
import com.app.demo.dao.mapper.MProductLikeMapper;
import com.app.demo.exception.SystemException;
import com.app.demo.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
public class MProductLikeDao {

    @Autowired
    MessageSource messageSource;

    @Autowired
    private MProductLikeMapper mProductLikeMapper;

    /**
     * ユーザーお気に入り商品情報を取得
     *
     * @author y_ha
     */
    public MProductLike findUserLiked(Integer userId, Integer productId) {
        try {
            return mProductLikeMapper.findUserLiked(userId, productId);
        } catch (Exception exception) {
            final String methodName = "ProductLikeMapper#findUserLiked";
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("userId", userId);
            paramMap.put("productId", productId);
            String overview = messageSource.getMessage(MessageIdConst.E_SQL_ISSUE, null, LocaleAspect.LOCALE);
            String detail = StringUtils.convertInterfaceErrorMsg(methodName, paramMap, exception);
            log.error(overview + detail);
            throw new SystemException(MessageIdConst.E_SQL_ISSUE, overview, detail);
        }
    }

    /**
     * ユーザーお気に入り商品情報をリストで取得
     *
     * @author y_ha
     */
    public List<MProductLike> findUserLikedList(Integer userId) {
        try {
            return mProductLikeMapper.findUserLikedList(userId);
        } catch (Exception exception) {
            final String methodName = "ProductLikeMapper#findUserLikedList";
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("userId", userId);
            String overview = messageSource.getMessage(MessageIdConst.E_SQL_ISSUE, null, LocaleAspect.LOCALE);
            String detail = StringUtils.convertInterfaceErrorMsg(methodName, paramMap, exception);
            log.error(overview + detail);
            throw new SystemException(MessageIdConst.E_SQL_ISSUE, overview, detail);
        }
    }

    /**
     * ユーザーお気に入り商品情報を登録する
     *
     * @author y_ha
     */
    public int insertRow(Integer userId, Integer productId) {
        try {
            return mProductLikeMapper.insertRow(userId, productId);
        } catch (Exception exception) {
            final String methodName = "ProductLikeMapper#insertRow";
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("userId", userId);
            paramMap.put("productId", productId);
            String overview = messageSource.getMessage(MessageIdConst.E_SQL_ISSUE, null, LocaleAspect.LOCALE);
            String detail = StringUtils.convertInterfaceErrorMsg(methodName, paramMap, exception);
            log.error(overview + detail);
            throw new SystemException(MessageIdConst.E_SQL_ISSUE, overview, detail);
        }
    }

    /**
     * ユーザーお気に入り商品情報を削除する
     *
     * @author y_ha
     */
    public int deleteRow(Integer userId, Integer productId) {
        try {
            return mProductLikeMapper.deleteRow(userId, productId);
        } catch (Exception exception) {
            final String methodName = "ProductLikeMapper#deleteRow";
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("userId", userId);
            paramMap.put("productId", productId);
            String overview = messageSource.getMessage(MessageIdConst.E_SQL_ISSUE, null, LocaleAspect.LOCALE);
            String detail = StringUtils.convertInterfaceErrorMsg(methodName, paramMap, exception);
            log.error(overview + detail);
            throw new SystemException(MessageIdConst.E_SQL_ISSUE, overview, detail);
        }
    }
}