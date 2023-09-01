package com.app.demo.dao.mapper;

import com.app.demo.dao.entity.MSocialComment;
import com.app.demo.dto.request.SocialCommentReqDto;
import com.app.demo.dto.response.SocialCommentResDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MSocialCommentMapper {
    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int deleteByPrimaryKey(Integer commentId);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int insert(MSocialComment row);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int insertSelective(MSocialComment row);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    MSocialComment selectByPrimaryKey(Integer commentId);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int updateByPrimaryKeySelective(MSocialComment row);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int updateByPrimaryKey(MSocialComment row);

    List<SocialCommentResDto> findCommentList(@Param("socialId") Integer socialId);

    int insertRow(@Param("userId")  Integer userId, @Param("comment") SocialCommentReqDto comment);

    int deleteRow(@Param("userId")  Integer userId, @Param("socialId") Integer socialId, @Param("commentId") Integer commentId);
}