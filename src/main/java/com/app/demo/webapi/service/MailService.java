package com.app.demo.webapi.service;

/**
 * メール機能サービス
 *
 * @author y_ha
 */
public interface MailService {

    public void sendAuthMail(String mail, String mailKey);
}
