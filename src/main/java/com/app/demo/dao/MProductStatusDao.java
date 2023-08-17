package com.app.demo.dao;

import com.app.demo.aspect.LocaleAspect;
import com.app.demo.constants.MessageIdConst;
import com.app.demo.dao.entity.MProductStatus;
import com.app.demo.dao.mapper.MProductStatusMapper;
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
public class MProductStatusDao {

    @Autowired
    MessageSource messageSource;

    @Autowired
    private MProductStatusMapper mProductStatusMapper;

    /**
     * 商品の状態情報を取得
     *
     * @author y_ha
     */
    public Map<Integer, MProductStatus> getStatusInfo() {
        try {
            Map<Integer, MProductStatus> res = new HashMap<>();
            List<MProductStatus> statusList = mProductStatusMapper.findAll();
            for (MProductStatus status : statusList) {
                res.put(status.getStatusId(), status);
            }
            return res;
        } catch (Exception exception) {
            final String methodName = "ProductStatusMapper#findAll";
            Map<String, Object> paramMap = new HashMap<>();
            String overview = messageSource.getMessage(MessageIdConst.E_SQL_ISSUE, null, LocaleAspect.LOCALE);
            String detail = StringUtils.convertInterfaceErrorMsg(methodName, paramMap, exception);
            log.error(overview + detail);
            throw new SystemException(MessageIdConst.E_SQL_ISSUE, overview, detail);
        }
    }
}