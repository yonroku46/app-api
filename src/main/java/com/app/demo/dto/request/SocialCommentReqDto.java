package com.app.demo.dto.request;

import lombok.Data;

import java.io.Serializable;

/**
 * ソーシャルコメント追加/削除DTO
 *
 * @author y_ha
 */
@Data
public class SocialCommentReqDto implements Serializable {

    /**
     * シリアルバージョンUID.
     */
    private static final long serialVersionUID = 2072948880507229001L;

    /**
     * ソーシャルID.
     */
    private Integer socialId;

    /**
     * 返信対象.
     */
    private Integer reply;

    /**
     * コメント内容.
     */
    private String contents;
}