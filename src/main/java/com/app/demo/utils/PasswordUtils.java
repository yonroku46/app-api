/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.demo.utils;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Random;

/**
 * パスワードに基づくJava暗号化/復号化
 */
@Component
public class PasswordUtils {

    private static final String KEY_ALGORITHM = "AES";
    
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";

    private static final String CHARACTERS = "abcdefghijklmnop1234567890123456";

    public static final String ENCRYPT_KEY = "1234567890123456";
    
    public static final String ENCRYPT_IV = "abcdefghijklmnop";


    /**
     * 暗号化メソッド
     *
     * @param text 暗号化する文字列
     * @return 暗号化文字列
     */
    public static String encode(String text) {
        // 変数初期化
        String strResult = null;

        try {
            // 文字列をバイト配列へ変換
            byte[] byteText = text.getBytes("UTF-8");

            // 暗号化キーと初期化ベクトルをバイト配列へ変換
            byte[] byteKey = ENCRYPT_KEY.getBytes("UTF-8");
            byte[] byteIv = ENCRYPT_IV.getBytes("UTF-8");

            // 暗号化キーと初期化ベクトルのオブジェクト生成
            SecretKeySpec key = new SecretKeySpec(byteKey, KEY_ALGORITHM);
            IvParameterSpec iv = new IvParameterSpec(byteIv);

            // Cipherオブジェクト生成
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);

            // Cipherオブジェクトの初期化
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);

            // 暗号化の結果格納
            byte[] byteResult = cipher.doFinal(byteText);

            // Base64へエンコード
            strResult = Base64.encodeBase64String(byteResult);

        } catch (Exception e) {
            return "";
        }
        // 暗号化文字列を返却
        return strResult;
    }

    /**
     * AES 復号化メソッド
     *
     * @param text 復号化する文字列
     * @return 復号化文字列
     */
    public static String decode(String text) {
        // 変数初期化
        String strResult = null;

        try {
            // Base64をデコード
            byte[] byteText = Base64.decodeBase64(text);

            // 暗号化キーと初期化ベクトルをバイト配列へ変換
            byte[] byteKey = ENCRYPT_KEY.getBytes("UTF-8");
            byte[] byteIv = ENCRYPT_IV.getBytes("UTF-8");

            // 復号化キーと初期化ベクトルのオブジェクト生成
            SecretKeySpec key = new SecretKeySpec(byteKey, KEY_ALGORITHM);
            IvParameterSpec iv = new IvParameterSpec(byteIv);

            // Cipherオブジェクト生成
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);

            // Cipherオブジェクトの初期化
            cipher.init(Cipher.DECRYPT_MODE, key, iv);

            // 復号化の結果格納
            byte[] byteResult = cipher.doFinal(byteText);

            // バイト配列を文字列へ変換
            strResult = new String(byteResult, "UTF-8");

        } catch (Exception e) {
            return "";
        }

        // 復号化文字列を返却
        return strResult;
    }

    /**
     * ランダムキー生成
     *
     * @param length 生成するキーの長さ
     * @return 生成されたキー
     */
    public static String generateRandomKey(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }

}
