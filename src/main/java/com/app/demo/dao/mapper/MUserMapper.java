package com.app.demo.dao.mapper;

import com.app.demo.dao.entity.MUser;
import com.app.demo.dao.entity.MUserExample;
import com.app.demo.dao.entity.MUserKey;
import com.app.demo.dto.MenuAuthInfoDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface MUserMapper {
    /**
     * @mbg.generated generated automatically, do not modify!
     */
    long countByExample(MUserExample example);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int deleteByExample(MUserExample example);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int deleteByPrimaryKey(@Param("uid") Integer uid, @Param("mail") String mail);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int insert(MUser row);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int insertSelective(MUser row);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    List<MUser> selectByExample(MUserExample example);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    MUser selectByPrimaryKey(@Param("uid") Integer uid, @Param("mail") String mail);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int updateByExampleSelective(@Param("row") MUser row, @Param("example") MUserExample example);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int updateByExample(@Param("row") MUser row, @Param("example") MUserExample example);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int updateByPrimaryKeySelective(MUser row);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int updateByPrimaryKey(MUser row);

    MUser login(@Param("mail") String mail);

    MenuAuthInfoDto getAccessibleInfo(@Param("userKey") MUserKey userKey, @Param("path") String path);
}