package com.layermark.layermark_sarismet.service;

import com.layermark.layermark_sarismet.model.CustomToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    private CryptoService cryptoService;

    @Autowired
    public void setCryptoService(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    public void sendEmail(HttpServletRequest request, String username) {

        String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request)
                .replacePath(null)
                .build()
                .toUriString();

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("sarismet2825@gmail.com");
        Date nw = new Date();
        long milliseconds = nw.getTime();
        CustomToken customToken = new CustomToken(milliseconds,username);

        String encryptedToken = cryptoService.encrypt(customToken);

        System.out.println("encryptedToken "+encryptedToken);

        String link = baseUrl+"/auth/register_verification/token="+encryptedToken;
        String mainBodyText =  String.format("Hello World %s \\n We have received your sign up request. Please click the link below to verify your email\\n Click link to verify %s", username,link); // String value
        msg.setSubject("Email Verification");
        msg.setText(mainBodyText);
        javaMailSender.send(msg);

    }
}