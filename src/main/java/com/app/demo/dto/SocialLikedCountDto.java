package com.app.demo.dto;

import com.app.demo.dto.response.core.ResponseData;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * ソーシャルカウント情報DTO
 *
 * @author y_ha
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SocialLikedCountDto extends ResponseData implements Serializable {

    private static final long serialVersionUID = 34895794379L;

    private Integer socialId;

    private Integer likedCount;
}
