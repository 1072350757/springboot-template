package com.baronj.template.util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailUtils {
    /**
     * 发送邮件
     *
     * @param toMail  发送给谁
     * @param title   主题
     * @param content 内容
     * @throws MessagingException
     */
    public static void sendMail(String toMail, String title, String content) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", "smtp.qq.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", 465);
        props.put("mail.smtp.ssl.enable", "true");
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("1072350757@qq.com", "hfenxgbkqlsbbeig");
            }
        });
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("1072350757@qq.com"));
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(toMail));
        message.setRecipient(MimeMessage.RecipientType.CC, new InternetAddress("1072350757@qq.com"));
        message.setSubject(title);
        message.setContent(content, "text/html;charset=utf-8");
        Transport.send(message);
    }
}
