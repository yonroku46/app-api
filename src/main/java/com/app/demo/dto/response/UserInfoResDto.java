package com.app.demo.dto.response;

import com.app.demo.dto.response.core.ResponseData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * ユーザー情報レスポンス用DTO
 *
 * @author y_ha
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserInfoResDto extends ResponseData implements Serializable {

    private static final long serialVersionUID = 34895794374L;

    private Integer uid;

    private String mail;

    private String userName;

    private String token;

    private String refreshToken;

    private Boolean mailAuth;
}
