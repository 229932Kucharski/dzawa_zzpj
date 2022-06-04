package pl.jawa.psinder.webclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service("emailService")
public class MailSender{

    @Autowired
    private JavaMailSender emailSender;

    public void sendVerificationMessage(String user, String regon, String address){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("psinderva@outlook.com");
        message.setTo("psinderva@gmail.com");
        message.setSubject(user + " verification");
        message.setText("Dzialam");
        emailSender.send(message);
    }

}
