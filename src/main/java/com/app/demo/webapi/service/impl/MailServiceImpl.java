package com.app.demo.webapi.service.impl;

import com.app.demo.aspect.LocaleAspect;
import com.app.demo.exception.ApplicationException;
import com.app.demo.utils.PasswordUtils;
import com.app.demo.webapi.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * メール機能サービス詳細
 *
 * @author y_ha
 * @version 0.0.1
 */
@Service
@Slf4j
public class MailServiceImpl implements MailService {

    @Autowired
    MessageSource messageSource;

    @Autowired
    JavaMailSender mailSender;

    @Value("${app.base.url}")
    private String BASE_URL;

    private static String CHAR_SET = "utf-8";

    private static String SUB_TYPE = "html";

    @Override
    public void sendAuthMail(String mail, String mailKey) {

        // メール送信
        try {
            String encryptMail = PasswordUtils.encode(mail);
            String encryptMailKey = PasswordUtils.encode(mailKey);

            MimeMessage mailObj = mailSender.createMimeMessage();
            mailObj.setSubject("【DemoApp】メール認証案内", CHAR_SET);
            mailObj.setText(new StringBuffer().append("<h3>メール認証</h3>")
                    .append("<p style='margin-left: 10px;'>下記のボタンをクリックすると、認証完了になります。</p>")
                    .append("<a style='display: inline-block; text-align: center; width: 60px; margin-top: 20px; margin-left: 10px; padding: 6px 14px; background-color: #007bff; color: #ffffff; text-decoration: none; border-radius: 4px;'")
                    .append(" href='")
                    .append(BASE_URL)
                    .append("/auth?m=")
                    .append(encryptMail)
                    .append("&k=")
                    .append(encryptMailKey)
                    .append("' target='_blenk'>認証確認</a>")
                    .toString(), CHAR_SET, SUB_TYPE);
            mailObj.setFrom("noreply@xxdadxx.jp");
            mailObj.addRecipients(Message.RecipientType.TO, mail);

            mailSender.send(mailObj);
        } catch (MessagingException e) {
            String message = messageSource.getMessage("error.mail.fail", null, LocaleAspect.LOCALE);
            log.warn(message);
            throw new ApplicationException(HttpStatus.OK, null, message);
        }
    }
}
