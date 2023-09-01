package com.app.demo.dao;

import com.app.demo.aspect.LocaleAspect;
import com.app.demo.constants.MessageIdConst;
import com.app.demo.dao.entity.MSocialLike;
import com.app.demo.dao.mapper.MSocialLikeMapper;
import com.app.demo.dto.SocialLikedCountDto;
import com.app.demo.exception.SystemException;
import com.app.demo.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class MSocialLikeDao {

    @Autowired
    MessageSource messageSource;

    @Autowired
    private MSocialLikeMapper mSocialLikeMapper;

    /**
     * ユーザーお気に入りソーシャル情報を取得
     *
     * @author y_ha
     */
    public MSocialLike findUserLiked(Integer userId, Integer socialId) {
        try {
            return mSocialLikeMapper.findUserLiked(userId, socialId);
        } catch (Exception exception) {
            final String methodName = "SocialLikeMapper#findUserLiked";
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("userId", userId);
            paramMap.put("socialId", socialId);
            String overview = messageSource.getMessage(MessageIdConst.E_SQL_ISSUE, null, LocaleAspect.LOCALE);
            String detail = StringUtils.convertInterfaceErrorMsg(methodName, paramMap, exception);
            log.error(overview + detail);
            throw new SystemException(MessageIdConst.E_SQL_ISSUE, overview, detail);
        }
    }

    /**
     * ユーザーお気に入りソーシャル情報をリストで取得
     *
     * @author y_ha
     */
    public List<MSocialLike> findUserLikedList(Integer userId) {
        try {
            return mSocialLikeMapper.findUserLikedList(userId);
        } catch (Exception exception) {
            final String methodName = "SocialLikeMapper#findUserLikedList";
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("userId", userId);
            String overview = messageSource.getMessage(MessageIdConst.E_SQL_ISSUE, null, LocaleAspect.LOCALE);
            String detail = StringUtils.convertInterfaceErrorMsg(methodName, paramMap, exception);
            log.error(overview + detail);
            throw new SystemException(MessageIdConst.E_SQL_ISSUE, overview, detail);
        }
    }

    /**
     * ユーザーお気に入りソーシャル情報を登録する
     *
     * @author y_ha
     */
    public int insertRow(Integer userId, Integer socialId) {
        try {
            return mSocialLikeMapper.insertRow(userId, socialId);
        } catch (Exception exception) {
            final String methodName = "SocialLikeMapper#insertRow";
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("userId", userId);
            paramMap.put("socialId", socialId);
            String overview = messageSource.getMessage(MessageIdConst.E_SQL_ISSUE, null, LocaleAspect.LOCALE);
            String detail = StringUtils.convertInterfaceErrorMsg(methodName, paramMap, exception);
            log.error(overview + detail);
            throw new SystemException(MessageIdConst.E_SQL_ISSUE, overview, detail);
        }
    }

    /**
     * ユーザーお気に入りソーシャル情報を削除する
     *
     * @author y_ha
     */
    public int deleteRow(Integer userId, Integer socialId) {
        try {
            return mSocialLikeMapper.deleteRow(userId, socialId);
        } catch (Exception exception) {
            final String methodName = "SocialLikeMapper#deleteRow";
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("userId", userId);
            paramMap.put("socialId", socialId);
            String overview = messageSource.getMessage(MessageIdConst.E_SQL_ISSUE, null, LocaleAspect.LOCALE);
            String detail = StringUtils.convertInterfaceErrorMsg(methodName, paramMap, exception);
            log.error(overview + detail);
            throw new SystemException(MessageIdConst.E_SQL_ISSUE, overview, detail);
        }
    }

    /**
     * ソーシャル毎のお気に入りカウントを取得する
     *
     * @author y_ha
     */
    public Map<Integer, Integer> getLikedCount(List<Integer> socialIdList) {
        try {
            List<SocialLikedCountDto> likedCountList = mSocialLikeMapper.getLikedCount(socialIdList);
            return likedCountList.stream()
                    .collect(Collectors.toMap(SocialLikedCountDto::getSocialId, SocialLikedCountDto::getLikedCount));
        } catch (Exception exception) {
            final String methodName = "SocialLikeMapper#getLikedCount";
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("socialIdList", socialIdList);
            String overview = messageSource.getMessage(MessageIdConst.E_SQL_ISSUE, null, LocaleAspect.LOCALE);
            String detail = StringUtils.convertInterfaceErrorMsg(methodName, paramMap, exception);
            log.error(overview + detail);
            throw new SystemException(MessageIdConst.E_SQL_ISSUE, overview, detail);
        }
    }
}