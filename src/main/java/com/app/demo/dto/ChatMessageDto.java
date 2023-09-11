package com.app.demo.dto;

import com.app.demo.dto.response.core.ResponseData;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * チャットメッセージ情報DTO
 *
 * @author y_ha
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ChatMessageDto extends ResponseData implements Serializable {

    private static final long serialVersionUID = 34895796379L;

    private String roomId;

    private Integer writer;

    private String message;

    private Date date;

    private String sessionId;
}
