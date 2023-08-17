package com.app.demo.dao;

import com.app.demo.aspect.LocaleAspect;
import com.app.demo.constants.MessageIdConst;
import com.app.demo.dao.entity.MProduct;
import com.app.demo.dao.mapper.MProductMapper;
import com.app.demo.dto.request.ProductFilterReqDto;
import com.app.demo.exception.SystemException;
import com.app.demo.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
public class MProductDao {

    @Autowired
    MessageSource messageSource;

    @Autowired
    private MProductMapper mProductMapper;

    /**
     * PKで商品の情報を取得
     *
     * @author y_ha
     */
    public MProduct findProductByPk(Integer productId) {
        try {
            return mProductMapper.findProduct(productId);
        } catch (Exception exception) {
            final String methodName = "ProductMapper#findProductByPk";
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("productId", productId);
            String overview = messageSource.getMessage(MessageIdConst.E_SQL_ISSUE, null, LocaleAspect.LOCALE);
            String detail = StringUtils.convertInterfaceErrorMsg(methodName, paramMap, exception);
            log.error(overview + detail);
            throw new SystemException(MessageIdConst.E_SQL_ISSUE, overview, detail);
        }
    }

    /**
     * 商品の情報をフィルター付きで取得
     *
     * @author y_ha
     */
    public List<MProduct> findProductByFilter(ProductFilterReqDto filter) {
        try {
            return mProductMapper.findProductByFilter(filter);
        } catch (Exception exception) {
            final String methodName = "ProductMapper#findProductByFilter";
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("filter", filter);
            String overview = messageSource.getMessage(MessageIdConst.E_SQL_ISSUE, null, LocaleAspect.LOCALE);
            String detail = StringUtils.convertInterfaceErrorMsg(methodName, paramMap, exception);
            log.error(overview + detail);
            throw new SystemException(MessageIdConst.E_SQL_ISSUE, overview, detail);
        }
    }
}