package com.app.demo.webapi.controller;

import com.app.demo.dto.request.ProductFilterReqDto;
import com.app.demo.dto.request.ProductHistoryReqDto;
import com.app.demo.dto.request.ProductLikeReqDto;
import com.app.demo.dto.response.core.ResponseDto;
import com.app.demo.webapi.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 商品機能コントローラー
 *
 * @author y_ha
 */
@RestController
@RequestMapping("/product")
@Slf4j
public class ProductController extends BaseController {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ProductService productService;

    @GetMapping("/info")
    public ResponseDto getProductInfo(@RequestParam("productId") Integer productId) {
        Integer userId = super.getCurrentUserId();
        return productService.getProductInfo(userId, productId);
    }

    @GetMapping("/filter")
    public ResponseDto getProductList(@Validated ProductFilterReqDto req) {
        Integer userId = super.getCurrentUserId();
        return productService.getProductList(userId, req);
    }

    @GetMapping("/history")
    public ResponseDto getProductHistory(@Validated ProductHistoryReqDto req) {
        return productService.getProductHistory(req);
    }

    @PutMapping("/like")
    public ResponseDto productLikeInsert(@RequestBody ProductLikeReqDto req) {
        Integer userId = super.getCurrentUserId();
        return productService.productLikeInsert(userId, req.getProductId());
    }

    @DeleteMapping("/like")
    public ResponseDto productLikeDelete(@RequestParam("productId") Integer productId) {
        Integer userId = super.getCurrentUserId();
        return productService.productLikeDelete(userId, productId);
    }
}
