package com.app.demo.dto.response;

import com.app.demo.dto.response.core.ResponseData;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ソーシャルコメント情報レスポンス用DTO
 *
 * @author y_ha
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SocialCommentResDto extends ResponseData implements Serializable {

    private static final long serialVersionUID = 34895794479L;

    private Integer commentId;

    private Integer socialId;

    private Integer reply;

    private List<SocialCommentResDto> replies;

    private Integer owner;

    private String name;

    private String profileImg;

    private String contents;

    private Date date;

    public SocialCommentResDto() {
        super();
        initialize();
    }

    public void initialize() {
        if (this.replies == null) {
            this.replies = new ArrayList<>();
        }
    }
}
