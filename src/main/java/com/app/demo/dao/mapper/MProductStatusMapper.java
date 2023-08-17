package com.app.demo.dao.mapper;

import com.app.demo.dao.entity.MProductStatus;

import java.util.List;

public interface MProductStatusMapper {
    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int deleteByPrimaryKey(Integer statusId);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int insert(MProductStatus row);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int insertSelective(MProductStatus row);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    MProductStatus selectByPrimaryKey(Integer statusId);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int updateByPrimaryKeySelective(MProductStatus row);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int updateByPrimaryKey(MProductStatus row);

    List<MProductStatus> findAll();
}