package com.app.demo.dto.response;

import com.app.demo.dto.response.core.ResponseData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 商品情報レスポンス用DTO
 *
 * @author y_ha
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProductInfoListResDto extends ResponseData implements Serializable {

    private static final long serialVersionUID = 34895794398L;

    private List<ProductInfoResDto> productList;
}
