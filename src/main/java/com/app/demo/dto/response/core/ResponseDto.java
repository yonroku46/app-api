package com.app.demo.dto.response.core;

import java.io.Serializable;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * レスポンスクラス
 *
 * @author y_ha
 */
@Data
@NoArgsConstructor
public class ResponseDto implements Serializable {

    private static final long serialVersionUID = 34895794379L;

    @Setter
    @Getter
    private int resultCode;

    @Setter
    private boolean hasErrors;

    @Setter
    @Getter
    private List<Information> informations;

    @Setter
    @Getter
    private List<Error> errors;

    @Setter
    @Getter
    private ResponseData responseData;

    public boolean hasErrors() {
        return hasErrors;
    }
}
