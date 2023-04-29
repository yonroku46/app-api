package com.app.demo.dto.response;

import com.app.demo.dto.response.core.ResponseData;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserInfoResDto extends ResponseData implements Serializable {

    private static final long serialVersionUID = 34895794374L;

    private String userId;

    private String userPw;
}
