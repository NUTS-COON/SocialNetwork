package ru.nutscoon.sn.core.service.impl;

import ru.nutscoon.sn.core.model.Email;
import ru.nutscoon.sn.core.model.EmailConfig;
import ru.nutscoon.sn.core.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailServiceImpl implements EmailService {

    private EmailConfig emailConfig;

    @Autowired
    public EmailServiceImpl(EmailConfig emailConfig) {
        this.emailConfig = emailConfig;
    }

    public void sendEmail(Email email) {
        Properties props = new Properties();
        props.put("mail.smtp.host", emailConfig.getHost());
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", emailConfig.getPort());
        props.put("mail.smtp.socketFactory.port", emailConfig.getPort());
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.connectiontimeout", "10000");
        props.put("mail.smtp.timeout", "10000");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailConfig.getUsername(), emailConfig.getPassword());
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailConfig.getFrom()));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email.getTo()));
            message.setSubject(email.getSubject());
            message.setText(email.getBody());

            Transport.send(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
