package com.app.demo.dao.mapper;

import com.app.demo.dao.entity.MSocialLike;
import com.app.demo.dto.SocialLikedCountDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MSocialLikeMapper {
    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int deleteByPrimaryKey(Integer likeId);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int insert(MSocialLike row);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int insertSelective(MSocialLike row);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    MSocialLike selectByPrimaryKey(Integer likeId);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int updateByPrimaryKeySelective(MSocialLike row);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int updateByPrimaryKey(MSocialLike row);

    MSocialLike findUserLiked(@Param("userId") Integer userId, @Param("socialId") Integer socialId);

    List<MSocialLike> findUserLikedList(@Param("userId") Integer userId);

    int insertRow(@Param("userId") Integer userId, @Param("socialId") Integer socialId);

    int deleteRow(@Param("userId") Integer userId, @Param("socialId") Integer socialId);

    List<SocialLikedCountDto> getLikedCount(@Param("socialIdList") List<Integer> socialIdList);
}