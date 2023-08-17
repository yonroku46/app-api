package com.app.demo.dao.mapper;

import com.app.demo.dao.entity.MProductHistory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MProductHistoryMapper {
    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int deleteByPrimaryKey(Integer historyId);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int insert(MProductHistory row);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int insertSelective(MProductHistory row);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    MProductHistory selectByPrimaryKey(Integer historyId);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int updateByPrimaryKeySelective(MProductHistory row);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int updateByPrimaryKey(MProductHistory row);

    List<MProductHistory> findHistoryInfo(@Param("productId") Integer productId, @Param("historyIdList") List<Integer> historyIdList);
}