package com.app.demo.dao;

import com.app.demo.aspect.LocaleAspect;
import com.app.demo.constants.MessageIdConst;
import com.app.demo.dao.entity.MProductHistory;
import com.app.demo.dao.mapper.MProductHistoryMapper;
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
public class MProductHistoryDao {

    @Autowired
    MessageSource messageSource;

    @Autowired
    private MProductHistoryMapper mProductHistoryMapper;

    /**
     * 商品の履歴情報を取得
     *
     * @author y_ha
     */
    public List<MProductHistory> getHistoryInfo(Integer productId, List<Integer> historyIdList) {
        try {
            return mProductHistoryMapper.findHistoryInfo(productId, historyIdList);
        } catch (Exception exception) {
            final String methodName = "ProductHistoryMapper#findHistoryInfo";
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("productId", productId);
            paramMap.put("historyIdList", historyIdList);
            String overview = messageSource.getMessage(MessageIdConst.E_SQL_ISSUE, null, LocaleAspect.LOCALE);
            String detail = StringUtils.convertInterfaceErrorMsg(methodName, paramMap, exception);
            log.error(overview + detail);
            throw new SystemException(MessageIdConst.E_SQL_ISSUE, overview, detail);
        }
    }
}