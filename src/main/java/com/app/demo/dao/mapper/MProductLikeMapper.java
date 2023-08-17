package com.app.demo.dao.mapper;

import com.app.demo.dao.entity.MProductLike;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MProductLikeMapper {
    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int deleteByPrimaryKey(Integer likeId);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int insert(MProductLike row);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int insertSelective(MProductLike row);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    MProductLike selectByPrimaryKey(Integer likeId);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int updateByPrimaryKeySelective(MProductLike row);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int updateByPrimaryKey(MProductLike row);

    MProductLike findUserLiked(@Param("userId") Integer userId, @Param("productId") Integer productId);

    List<MProductLike> findUserLikedList(@Param("userId") Integer userId);

    int insertRow(@Param("userId") Integer userId, @Param("productId") Integer productId);

    int deleteRow(@Param("userId") Integer userId, @Param("productId") Integer productId);
}