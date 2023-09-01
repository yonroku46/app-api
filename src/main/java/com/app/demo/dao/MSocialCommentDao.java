package com.app.demo.dao;

import com.app.demo.aspect.LocaleAspect;
import com.app.demo.constants.MessageIdConst;
import com.app.demo.dao.entity.MSocialComment;
import com.app.demo.dao.mapper.MSocialCommentMapper;
import com.app.demo.dto.request.SocialCommentReqDto;
import com.app.demo.dto.response.SocialCommentResDto;
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
public class MSocialCommentDao {

    @Autowired
    MessageSource messageSource;

    @Autowired
    private MSocialCommentMapper mSocialCommentMapper;

    /**
     * ソーシャルのコメント情報をリストで取得
     *
     * @author y_ha
     */
    public List<SocialCommentResDto> findCommentList(Integer socialId) {
        try {
            return mSocialCommentMapper.findCommentList(socialId);
        } catch (Exception exception) {
            final String methodName = "SocialCommentMapper#findCommentList";
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("socialId", socialId);
            String overview = messageSource.getMessage(MessageIdConst.E_SQL_ISSUE, null, LocaleAspect.LOCALE);
            String detail = StringUtils.convertInterfaceErrorMsg(methodName, paramMap, exception);
            log.error(overview + detail);
            throw new SystemException(MessageIdConst.E_SQL_ISSUE, overview, detail);
        }
    }

    /**
     * ユーザーソーシャルコメント情報を登録する
     *
     * @author y_ha
     */
    public int insertRow(Integer userId, SocialCommentReqDto comment) {
        try {
            return mSocialCommentMapper.insertRow(userId, comment);
        } catch (Exception exception) {
            final String methodName = "SocialCommentMapper#insertRow";
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("userId", userId);
            paramMap.put("comment", comment);
            String overview = messageSource.getMessage(MessageIdConst.E_SQL_ISSUE, null, LocaleAspect.LOCALE);
            String detail = StringUtils.convertInterfaceErrorMsg(methodName, paramMap, exception);
            log.error(overview + detail);
            throw new SystemException(MessageIdConst.E_SQL_ISSUE, overview, detail);
        }
    }

    /**
     * ユーザーソーシャルコメント情報を削除する
     *
     * @author y_ha
     */
    public int deleteRow(Integer userId, Integer socialId, Integer commentId) {
        try {
            return mSocialCommentMapper.deleteRow(userId, socialId, commentId);
        } catch (Exception exception) {
            final String methodName = "SocialCommentMapper#deleteRow";
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("userId", userId);
            paramMap.put("socialId", socialId);
            paramMap.put("commentId", commentId);
            String overview = messageSource.getMessage(MessageIdConst.E_SQL_ISSUE, null, LocaleAspect.LOCALE);
            String detail = StringUtils.convertInterfaceErrorMsg(methodName, paramMap, exception);
            log.error(overview + detail);
            throw new SystemException(MessageIdConst.E_SQL_ISSUE, overview, detail);
        }
    }

}