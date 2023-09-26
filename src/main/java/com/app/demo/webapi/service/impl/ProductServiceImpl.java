package com.app.demo.webapi.service.impl;

import com.app.demo.aspect.LocaleAspect;
import com.app.demo.constants.MessageIdConst;
import com.app.demo.dao.*;
import com.app.demo.dao.entity.*;
import com.app.demo.dto.request.*;
import com.app.demo.dto.response.FlgResDto;
import com.app.demo.dto.response.ProductInfoListResDto;
import com.app.demo.dto.response.ProductInfoResDto;
import com.app.demo.dto.response.core.Information;
import com.app.demo.dto.response.core.ResponseDto;
import com.app.demo.utils.*;
import com.app.demo.webapi.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * ユーザー機能サービス詳細
 *
 * @author y_ha
 */
@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    MessageSource messageSource;

    @Autowired
    private MProductDao mProductDao;

    @Autowired
    private MProductLikeDao mProductLikeDao;

    @Autowired
    private MProductStatusDao mProductStatusDao;

    @Autowired
    private MProductCategoryDao mProductCategoryDao;

    @Autowired
    private MProductHistoryDao mProductHistoryDao;

    @Override
    public ResponseDto getProductInfo(Integer userId, Integer productId) {
        ProductInfoResDto res = null;
        MProduct product  = mProductDao.findProductByPk(productId);

        if (product != null) {
            // 状態情報取得
            Map<Integer, MProductStatus> statusInfo = mProductStatusDao.getStatusInfo();
            // カテゴリー情報取得
            Map<Integer, MProductCategory> categoryInfo = mProductCategoryDao.getCategoryInfo();
            res = covertProductInfo(product, statusInfo, categoryInfo);
            // お気に入り判定
            if (userId != null) {
                MProductLike liked = mProductLikeDao.findUserLiked(userId, productId);
                res.setLiked(liked != null);
            }
        }

        return ResponseUtils.generateDtoSuccess(new Information(MessageIdConst.I_GETTING_SUCCESS,
                messageSource.getMessage(MessageIdConst.I_GETTING_SUCCESS, new String[]{"ProductInfo"}, LocaleAspect.LOCALE)), res);
    }

    @Override
    public ResponseDto getProductList(Integer userId, ProductFilterReqDto filter) {
        List<ProductInfoResDto> res = new ArrayList<>();

        // フィルターで商品リストを取得
        List<MProduct> productList = mProductDao.findProductByFilter(filter);
        // 状態マスター情報取得
        Map<Integer, MProductStatus> statusInfo = mProductStatusDao.getStatusInfo();
        // カテゴリー情報取得
        Map<Integer, MProductCategory> categoryInfo = mProductCategoryDao.getCategoryInfo();

        // 表示用データで加工
        for (MProduct product : productList) {
            res.add(covertProductInfo(product, statusInfo, categoryInfo));
        }

        // お気に入り判定
        if (userId != null) {
            List<MProductLike> likedList = mProductLikeDao.findUserLikedList(userId);
            for (ProductInfoResDto product : res) {
                boolean isLiked = false;
                for (MProductLike liked : likedList) {
                    if (product.getProductId() == liked.getProductId()) {
                        isLiked = true;
                        break;
                    }
                }
                product.setLiked(isLiked);
            }
        }

        return ResponseUtils.generateDtoSuccess(new Information(MessageIdConst.I_GETTING_SUCCESS,
                messageSource.getMessage(MessageIdConst.I_GETTING_SUCCESS, new String[]{"ProductList"}, LocaleAspect.LOCALE)), new ProductInfoListResDto(res));
    }

    @Override
    public ResponseDto getProductHistory(ProductHistoryReqDto req) {
        List<ProductInfoResDto> res = new ArrayList<>();

        if (!CollectionUtils.isEmpty(req.getProductIdList())) {
            // 状態マスター情報取得
            Map<Integer, MProductStatus> statusInfo = mProductStatusDao.getStatusInfo();
            List<MProductHistory> historyInfoList = mProductHistoryDao.getHistoryInfo(req.getProductId(), req.getProductIdList());
            // 履歴変換
            for (MProductHistory history : historyInfoList) {
                res.add(covertProductHistoryInfo(history, statusInfo));
            }
        }

        return ResponseUtils.generateDtoSuccess(new Information(MessageIdConst.I_GETTING_SUCCESS,
                messageSource.getMessage(MessageIdConst.I_GETTING_SUCCESS, new String[]{"ProductHistory"}, LocaleAspect.LOCALE)), new ProductInfoListResDto(res));
    }

    @Override
    public ResponseDto productLikeInsert(Integer userId, Integer productId) {
        FlgResDto res = new FlgResDto();

        mProductLikeDao.insertRow(userId, productId);
        res.setIsFlg(Boolean.TRUE);

        return ResponseUtils.generateDtoSuccess(new Information(MessageIdConst.I_GETTING_SUCCESS,
                messageSource.getMessage(MessageIdConst.I_INSERT_SUCCESS, new String[]{"ProductLike"}, LocaleAspect.LOCALE)), res);
    }

    @Override
    public ResponseDto productLikeDelete(Integer userId, Integer productId) {
        FlgResDto res = new FlgResDto();

        mProductLikeDao.deleteRow(userId, productId);
        res.setIsFlg(Boolean.TRUE);

        return ResponseUtils.generateDtoSuccess(new Information(MessageIdConst.I_GETTING_SUCCESS,
                messageSource.getMessage(MessageIdConst.I_DELETE_SUCCESS, new String[]{"ProductLike"}, LocaleAspect.LOCALE)), res);
    }

    public ProductInfoResDto covertProductInfo(MProduct product, Map<Integer, MProductStatus> statusInfo, Map<Integer, MProductCategory> categoryInfo) {
        ProductInfoResDto info = new ProductInfoResDto();
        info.setProductId(product.getProductId());
        info.setOwner(product.getCurrentOwner());
        info.setName(product.getProductName());
        info.setImgs(StringUtils.stringToStringList(product.getProductImgs()));
        info.setPrice(product.getPrice());
        info.setPriceSale(product.getPriceSale());
        info.setBrand(product.getBrand());
        info.setColors(StringUtils.stringToStringList(product.getColors()));
        info.setStatus(statusInfo.get(product.getStatus()).getStatusName());
        info.setSize(StringUtils.jsonStringToMap(product.getSize()));
        info.setSizeIdx(product.getSizeIdx());
        info.setMainCategory(categoryInfo.get(product.getCategory()).getMainCategory());
        info.setSubCategory(categoryInfo.get(product.getCategory()).getSubCategory());
        info.setGender(product.getGender());
        info.setTags(StringUtils.stringToStringList(product.getTags()));
        info.setAdditional(StringUtils.jsonStringToMap(product.getAdditional()));
        info.setHistory(StringUtils.stringToIntegerList(product.getHistory()));
        info.setDate(product.getCreateTime());

        return info;
    }

    public ProductInfoResDto covertProductHistoryInfo(MProductHistory productHistory, Map<Integer, MProductStatus> statusInfo) {
        ProductInfoResDto info = new ProductInfoResDto();
        info.setProductId(productHistory.getProductId());
        info.setImgs(StringUtils.stringToStringList(productHistory.getProductImgs()));
        info.setPrice(productHistory.getPrice());
        info.setStatus(statusInfo.get(productHistory.getStatus()).getStatusName());
        info.setDate(productHistory.getCreateTime());

        return info;
    }
}