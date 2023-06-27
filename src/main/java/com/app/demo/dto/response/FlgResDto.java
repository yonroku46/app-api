package com.app.demo.dto.response;

import com.app.demo.dto.response.core.ResponseData;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * フラグレスポンス用DTO
 *
 * @author y_ha
 * @version 0.0.1
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FlgResDto extends ResponseData implements Serializable {

    private static final long serialVersionUID = 34895794474L;

    private Boolean flg;
}
