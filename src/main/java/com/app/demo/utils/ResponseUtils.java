package com.app.demo.utils;

import java.util.ArrayList;
import java.util.List;

import com.app.demo.dto.response.core.Error;
import com.app.demo.dto.response.core.Information;
import com.app.demo.dto.response.core.ResponseData;
import com.app.demo.dto.response.core.ResponseDto;
import com.app.demo.enums.Result;
import com.app.demo.exception.ApplicationException;
import org.springframework.http.HttpStatus;

/**
 * レスポンス返却関連ユティリティー
 *
 * @author y_ha
 */
public class ResponseUtils {

    /**
     * インスタンス生成制御（サブクラスのみ許容）.
     */
    public ResponseUtils() {
    }

    /**
     * クライアントエラーが生じているかチェック
     *
     * @param exception
     */
    public static void isClientError(ApplicationException exception) {
        if (exception.getStatus() == null) {
            return;
        }
        // HTTPステータス:200以外の場合、引数の例外をスロー
        if (!HttpStatus.OK.equals(exception.getStatus())) {
            throw exception;
        }
    }

    /**
     * HTTPステータス200：正常系レスポンス生成（メッセージ単数）
     *
     * @param information メッセージ情報
     * @param responseData
     * @return
     */
    public static ResponseDto generateDtoSuccess(Information information, ResponseData responseData) {
        return generateDto(Result.SUCCESS, false, information, null, responseData);
    }

    /**
     * HTTPステータス200：正常系レスポンス生成（メッセージ複数）
     *
     * @param informations メッセージ情報リスト
     * @param responseData
     * @return
     */
    public static ResponseDto generateDtoSuccess(List<Information> informations, ResponseData responseData) {
        return generateDto(Result.SUCCESS, false, informations, null, responseData);
    }

    /**
     * HTTPステータス200：異常系レスポンス生成
     *
     * @param information
     * @param responseData
     * @return
     */
    public static ResponseDto generateDtoSuccessAbnormal(Information information, ResponseData responseData) {
        return generateDto(Result.SUCCESS, true, information, null, responseData);
    }

    public static ResponseDto generateDtoFailed(Error error) {
        return generateDto(Result.FAILED, true, null, error, null);
    }

    public static ResponseDto generateDtoFailed(List<Error> errors) {
        return generateDto(Result.FAILED, true, null, errors, null);
    }

    public static ResponseDto generateDtoFailed(Information information, Error error) {
        return generateDto(Result.FAILED, true, information, error, null);
    }

    public static ResponseDto generateDtoFailed(List<Information> informations, List<Error> errors) {
        return generateDto(Result.FAILED, true, informations, errors, null);
    }

    public static ResponseDto generateDtoFailed(Information information, Error error, ResponseData responseData) {
        return generateDto(Result.FAILED, true, information, error, responseData);
    }

    public static ResponseDto generateDtoFailed(List<Information> informations, List<Error> errors, ResponseData responseData) {
        return generateDto(Result.FAILED, true, informations, errors, responseData);
    }

    public static ResponseDto generateDto(Result result, boolean hasErrors, Information information, Error error, ResponseData responseData) {
        List<Information> informations = null;
        if (information != null) {
            informations = new ArrayList<>();
            informations.add(information);
        }

        List<Error> errors = null;
        if (error != null) {
            errors = new ArrayList<>();
            errors.add(error);
        }

        return generateDto(result, hasErrors, informations, errors, responseData);
    }

    /**
     * レスポンスDTO生成
     *
     * @param result
     * @param hasErrors
     * @param informations
     * @param errors
     * @param responseData
     * @return
     */
    public static ResponseDto generateDto(Result result, boolean hasErrors, List<Information> informations, List<Error> errors, ResponseData responseData) {
        ResponseDto dto = new ResponseDto();
        dto.setResultCode(result.getCode());
        dto.setHasErrors(hasErrors);
        dto.setInformations(informations);
        dto.setErrors(errors);
        dto.setResponseData(responseData);

        return dto;
    }
}
