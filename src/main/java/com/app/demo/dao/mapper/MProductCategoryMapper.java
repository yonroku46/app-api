package com.app.demo.dao.mapper;

import com.app.demo.dao.entity.MProductCategory;

import java.util.List;

public interface MProductCategoryMapper {
    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int deleteByPrimaryKey(Integer categoryId);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int insert(MProductCategory row);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int insertSelective(MProductCategory row);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    MProductCategory selectByPrimaryKey(Integer categoryId);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int updateByPrimaryKeySelective(MProductCategory row);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int updateByPrimaryKey(MProductCategory row);

    List<MProductCategory> findAll();
}