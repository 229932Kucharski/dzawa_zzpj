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

    public void sendVerificationMessage(String login, String fName, String lName, Long regon, String address){
        StringBuilder messageText = new StringBuilder("Data for verification:\n\n");
        messageText.append("User: ").append(fName).append(" ").append(lName).append(" (").append(login).append(")\n");
        int chceckNum = 0, d = 100000000;
        long r = regon;
        for (int i = 1; i < 9; i++) {
            chceckNum += i * (int)(r/d);
            r -= r - r%d;
            d /= 10;
        }
        messageText.append("Regon number: ").append(regon).append(", check number: ").append(chceckNum%11).append("\n");
        messageText.append("Address: ").append(address).append("\n\n");
        messageText.append("To validate the ").append(login).append(" user verification, please follow the link below: \n\n");
        messageText.append("https://localhost:8080/users/1/verified\n\n");

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("psinderva@outlook.com");
        message.setTo("psinderva@gmail.com");
        message.setSubject(login + " verification");
        message.setText(messageText.toString());
        emailSender.send(message);
    }

}
