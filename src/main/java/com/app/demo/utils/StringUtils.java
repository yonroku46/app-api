package com.app.demo.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * 文字列操作用ユティリティー
 *
 * @author y_ha
 */
@Component
public class StringUtils {

    /**
     * インスタンス生成制御（サブクラスのみ許容）.
     */
    protected StringUtils() {
    }

    /**
     * ObjectMapperのstatic変数
     */
    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * チャネル項目名プリフィックス.
     */
    private static final String CH_PREFIX = "ch_";

    /**
     * 半角英数記号のみの正規表現.
     */
    private static final Pattern PATTERN_HANKAKU = Pattern.compile("^[ -~｡-･]*$");

    /**
     * 空文字かを判定する
     *
     * @param val 対象文字列
     * @return true:未設定 / false:設定済み
     */
    public static boolean isEmpty(String val) {
        return val == null || val.length() == 0;
    }

    /**
     * 空文字以外かを判定する
     *
     * @param val 対象文字列
     * @return true:設定済み / false:未設定
     */
    public static boolean isNotEmpty(String val) {
        return !isEmpty(val);
    }

    /**
     * インターフェースエラーメッセージ変換（パラメータなし）
     *
     * @param methodName メソッド名
     * @param exception 発生元例外
     * @return 変換文字列
     */
    public static String convertInterfaceErrorMsg(String methodName, Exception exception) {
        return convertInterfaceErrorMsg(methodName, null, exception);
    }

    /**
     * インターフェースエラーメッセージ変換（パラメータあり）
     *
     * @param methodName メソッド名
     * @param param パラメータ（Dto、または、HashMap）
     * @param exception 発生元例外
     * @return 変換文字列
     */
    public static String convertInterfaceErrorMsg(String methodName, Object param, Exception exception) {
        StringBuilder builder = new StringBuilder();
        builder.append("method name=");
        builder.append(methodName);
        builder.append(" param=");
        if (param != null) {
            // パラメータをJSON文字列に変換して設定
            builder.append(StringUtils.convertObjectToJsonString(param));
        } else {
            builder.append("none");
        }
        builder.append(" error detail=");
        // スタックトレースを文字列変換して設定
        builder.append(convertStackTraceToString(exception));
        return builder.toString();
    }

    /**
     * スタックトレース→文字列変換処理
     *
     * @param exception 例外クラス
     * @return スタックトレース文字列
     */
    public static String convertStackTraceToString(Exception exception) {
        String stackTraceStr = null;

        StringWriter writer = new StringWriter();
        // スタックトレースを文字列に変換
        exception.printStackTrace(new PrintWriter(writer));
        stackTraceStr = writer.toString();

        // GC用にnullを設定
        writer = null;

        return stackTraceStr;

    }

    /**
     * オブジェクト→JSON文字列変換処理
     *
     * @param object オブジェクト
     * @return
     */
    public static String convertObjectToJsonString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    /**
     * ()付に変換
     *
     * @param str
     * @return (str)
     */
    public static String convertParentheses(String str) {
        if (StringUtils.isNotEmpty(str)) {
            return "(" + str + ")";
        } else {
            return null;
        }
    }

    /**
     * 半角英数記号のみかチェック
     *
     * @param str チェックする文字列
     * @return true:半角英数記号のみ/false:半角英数記号以外を含む
     */
    public static boolean isHankakuOnly(String str) {

        //nullまたは空文字の場合はfalseを返す
        if (str == null || "".equals(str)) {
            return false;
        }

        //半角英数記号を判定
        Matcher m = PATTERN_HANKAKU.matcher(str);

        return m.find();
    }

    /**
     * JSON文字をMAP型に変換
     *
     * @param json JSON文字列
     * @return
     */
    public static Map<String, Object> jsonStringToMap(String json) {
        Map<String, Object> resultMap = null;
        try {
            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, Object>>(){}.getType();
            resultMap = gson.fromJson(json, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    /**
     * 文字をList<String>型に変換
     *
     * @param str Array文字列
     * @return
     */
    public static List<String> stringToStringList(String str) {
        List<String> resultList = new ArrayList<>();
        try {
            Gson gson = new Gson();
            resultList = gson.fromJson(str, new TypeToken<List<String>>() {}.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }

    /**
     * 文字をList<Integer>型に変換
     *
     * @param str Array文字列
     * @return
     */
    public static List<Integer> stringToIntegerList(String str) {
        List<Integer> resultList = new ArrayList<>();
        try {
            Gson gson = new Gson();
            List<String> parsedList = gson.fromJson(str, new TypeToken<List<String>>() {}.getType());
            for (String element : parsedList) {
                if (isNumeric(element)) {
                    Integer number = Integer.parseInt(element);
                    resultList.add(number);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }

    /**
     * 文字が数字か判断
     *
     * @param str 文字列
     * @return
     */
    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }
}