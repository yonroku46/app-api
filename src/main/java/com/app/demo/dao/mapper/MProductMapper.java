package com.app.demo.dao.mapper;

import com.app.demo.dao.entity.MProduct;
import com.app.demo.dto.request.ProductFilterReqDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MProductMapper {
    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int deleteByPrimaryKey(Integer productId);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int insert(MProduct row);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int insertSelective(MProduct row);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    MProduct selectByPrimaryKey(Integer productId);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int updateByPrimaryKeySelective(MProduct row);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int updateByPrimaryKey(MProduct row);

    MProduct findProduct(@Param("productId") Integer productId);

    List<MProduct> findProductByFilter(@Param("filter") ProductFilterReqDto filter);
}