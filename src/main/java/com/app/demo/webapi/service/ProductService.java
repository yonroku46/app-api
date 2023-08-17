package com.app.demo.webapi.service;

import com.app.demo.dto.request.ProductFilterReqDto;
import com.app.demo.dto.request.ProductHistoryReqDto;
import com.app.demo.dto.response.core.ResponseDto;

/**
 * 商品機能サービス
 *
 * @author y_ha
 */
public interface ProductService {

    public ResponseDto getProductInfo(Integer userId, Integer productId);

    public ResponseDto getProductList(Integer userId, ProductFilterReqDto req);

    public ResponseDto getProductHistory(ProductHistoryReqDto req);

    public ResponseDto productLikeInsert(Integer userId, Integer productId);

    public ResponseDto productLikeDelete(Integer userId, Integer productId);
}
