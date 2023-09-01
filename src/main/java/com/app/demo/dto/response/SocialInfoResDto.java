package com.app.demo.dto.response;

import com.app.demo.dto.response.core.ResponseData;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * ソーシャル情報レスポンス用DTO
 *
 * @author y_ha
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SocialInfoResDto extends ResponseData implements Serializable {

    private static final long serialVersionUID = 34895794378L;

    private Integer socialId;

    private Integer owner;

    private String name;

    private String profileImg;

    private Integer profileHeight;

    private List<String> imgs;

    private String contents;

    private List<String> tags;

    private Date date;

    private Boolean liked;

    private Integer likedCount;
}
