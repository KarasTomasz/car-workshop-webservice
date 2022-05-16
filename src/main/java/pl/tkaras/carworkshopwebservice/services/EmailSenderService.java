package pl.tkaras.carworkshopwebservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@RequiredArgsConstructor
@Service
public class EmailSenderService{

    @Value("${emailSender.email}")
    private String appOwnerEmail;

    @Value("${emailSender.host}")
    private String hostEmail;

    @Value("${emailSender.port}")
    private Integer portEmail;

    @Value("${emailSender.username}")
    private String usernameEmail;

    @Value("${emailSender.password}")
    private String passwordEmail;

    @Async
    public void send(String email, String content, boolean isHtmlContent) throws MessagingException {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(hostEmail);
        mailSender.setPort(portEmail);
        mailSender.setUsername(usernameEmail);
        mailSender.setPassword(passwordEmail);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage ,"utf-8");
        mimeMessageHelper.setText(content, isHtmlContent);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Please verify your registration");
        mimeMessageHelper.setFrom(appOwnerEmail);
        mailSender.send(mimeMessage);
    }
}