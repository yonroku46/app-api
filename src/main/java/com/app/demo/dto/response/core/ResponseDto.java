package com.app.demo.dto.response.core;

import java.io.Serializable;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * レスポンスクラス
 *
 * @author y_ha
 */
@Data
@NoArgsConstructor
public class ResponseDto<T> implements Serializable {

    private static final long serialVersionUID = 34895794379L;

    private int resultCode;

    private boolean hasErrors;

    private List<Information> informations;

    private List<Error> errors;

    private ResponseData responseData;
}
