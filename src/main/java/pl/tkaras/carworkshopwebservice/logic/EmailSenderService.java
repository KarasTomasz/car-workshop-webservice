package pl.tkaras.carworkshopwebservice.logic;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailSenderService {

    private final String appOwnerEmail;

    private final JavaMailSender javaMailSender;

    public EmailSenderService(@Value("${emailSender.email}") String appOwnerEmail, JavaMailSender javaMailSender) {
        this.appOwnerEmail = appOwnerEmail;
        this.javaMailSender = javaMailSender;
    }


    public void send(String email, String content) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage ,true);
        mimeMessageHelper.setText(content);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Confirmation email");
        mimeMessageHelper.setFrom(appOwnerEmail);
        javaMailSender.send(mimeMessage);
    }

}
