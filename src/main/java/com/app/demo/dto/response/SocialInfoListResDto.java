package com.app.demo.dto.response;

import com.app.demo.dto.response.core.ResponseData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * ソーシャル情報リストレスポンス用DTO
 *
 * @author y_ha
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SocialInfoListResDto extends ResponseData implements Serializable {

    private static final long serialVersionUID = 34895794398L;

    private List<SocialInfoResDto> socialList;
}
