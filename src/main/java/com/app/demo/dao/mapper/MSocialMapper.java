package com.app.demo.dao.mapper;

import com.app.demo.dao.entity.MSocial;
import com.app.demo.dto.request.SocialFilterReqDto;
import com.app.demo.dto.response.SocialInfoResDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MSocialMapper {
    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int deleteByPrimaryKey(Integer socialId);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int insert(MSocial row);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int insertSelective(MSocial row);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    MSocial selectByPrimaryKey(Integer socialId);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int updateByPrimaryKeySelective(MSocial row);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int updateByPrimaryKey(MSocial row);

    SocialInfoResDto findSocial(@Param("socialId") Integer socialId);

    List<SocialInfoResDto> findSocialByFilter(SocialFilterReqDto filter);
}