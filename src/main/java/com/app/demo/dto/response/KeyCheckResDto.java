package com.app.demo.dto.response;

import com.app.demo.dto.response.core.ResponseData;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * メール認証情報レスポンス用DTO
 *
 * @author y_ha
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class KeyCheckResDto extends ResponseData implements Serializable {

    private static final long serialVersionUID = 34895794374L;

    private String mail;
}
