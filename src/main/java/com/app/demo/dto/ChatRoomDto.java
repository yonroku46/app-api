package com.app.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * チャットルーム情報DTO
 *
 * @author y_ha
 */
@Getter
@Setter
public class ChatRoomDto {

    private String roomId;

    private String roomType;

    private String roomName;

    private Integer owner;

    private List<Integer> participant;
}
