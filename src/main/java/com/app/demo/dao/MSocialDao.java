package com.app.demo.dao;

import com.app.demo.aspect.LocaleAspect;
import com.app.demo.constants.MessageIdConst;
import com.app.demo.dao.mapper.MSocialMapper;
import com.app.demo.dto.request.SocialFilterReqDto;
import com.app.demo.dto.response.SocialInfoResDto;
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
public class MSocialDao {

    @Autowired
    MessageSource messageSource;

    @Autowired
    private MSocialMapper mSocialMapper;

    /**
     * PKでソーシャルの情報を取得
     *
     * @author y_ha
     */
    public SocialInfoResDto findSocialByPk(Integer socialId) {
        try {
            return mSocialMapper.findSocial(socialId);
        } catch (Exception exception) {
            final String methodName = "SocialMapper#findSocialByPk";
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("socialId", socialId);
            String overview = messageSource.getMessage(MessageIdConst.E_SQL_ISSUE, null, LocaleAspect.LOCALE);
            String detail = StringUtils.convertInterfaceErrorMsg(methodName, paramMap, exception);
            log.error(overview + detail);
            throw new SystemException(MessageIdConst.E_SQL_ISSUE, overview, detail);
        }
    }

    /**
     * ソーシャルの情報をフィルター付きで取得
     *
     * @author y_ha
     */
    public List<SocialInfoResDto> findSocialByFilter(SocialFilterReqDto filter) {
        try {
            return mSocialMapper.findSocialByFilter(filter);
        } catch (Exception exception) {
            final String methodName = "SocialMapper#findSocialByFilter";
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("filter", filter);
            String overview = messageSource.getMessage(MessageIdConst.E_SQL_ISSUE, null, LocaleAspect.LOCALE);
            String detail = StringUtils.convertInterfaceErrorMsg(methodName, paramMap, exception);
            log.error(overview + detail);
            throw new SystemException(MessageIdConst.E_SQL_ISSUE, overview, detail);
        }
    }
}