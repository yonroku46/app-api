package com.app.demo.dto.response;

import com.app.demo.dto.response.core.ResponseData;
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
@EqualsAndHashCode(callSuper = true)
public class ProductInfoResDto extends ResponseData implements Serializable {

    private static final long serialVersionUID = 34895794378L;

    private Integer productId;

    private Integer owner;

    private String name;

    private List<String> imgs;

    private Integer price;

    private Integer priceSale;

    private String brand;

    private List<String> colors;

    private String status;

    private Map size;

    private Integer sizeIdx;

    private String mainCategory;

    private String subCategory;

    private String gender;

    private List<String> tags;

    private Map additional;

    private List<Integer> history;

    private Date date;

    private Boolean liked;
}
