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
    int deleteByPrimaryKey(String mail);

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
    MUser selectByPrimaryKey(String mail);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int updateByPrimaryKeySelective(MUser row);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int updateByPrimaryKey(MUser row);

    MUser findUserByMail(@Param("mail") String mail);

    MUser findSocialUser(@Param("suid") String suid, @Param("mail") String mail);

    MUser findUser(@Param("userKey") MUserKey userKey);

    MUser findMailKeyUser(@Param("mail") String mail, @Param("mailKey") String mailKey);

    MenuAuthInfoDto getAccessibleInfo(@Param("userKey") MUserKey userKey, @Param("path") String path);
}