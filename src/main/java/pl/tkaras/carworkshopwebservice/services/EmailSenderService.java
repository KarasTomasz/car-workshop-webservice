package pl.tkaras.carworkshopwebservice.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailSenderService{

    private final String appOwnerEmail;
    private final JavaMailSender javaMailSender;

    public EmailSenderService(@Value("${emailSender.email}") String appOwnerEmail, JavaMailSender javaMailSender) {
        this.appOwnerEmail = appOwnerEmail;
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void send(String email, String content, boolean isHtmlContent) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage ,"utf-8");
        mimeMessageHelper.setText(content, isHtmlContent);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Please verify your registration");
        mimeMessageHelper.setFrom(appOwnerEmail);
        javaMailSender.send(mimeMessage);
    }

}
