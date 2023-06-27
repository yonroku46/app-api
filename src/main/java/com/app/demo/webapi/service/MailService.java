package com.app.demo.webapi.service;

/**
 * メール機能サービス
 *
 * @author y_ha
 * @version 0.0.1
 */
public interface MailService {

    public void sendAuthMail(String mail, String mailKey);
}
