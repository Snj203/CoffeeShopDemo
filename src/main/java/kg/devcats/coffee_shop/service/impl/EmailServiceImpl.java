package kg.devcats.coffee_shop.service.impl;

import kg.devcats.coffee_shop.entity.h2.User;
import kg.devcats.coffee_shop.exceptions.EmailTokenExpiredException;
import kg.devcats.coffee_shop.repository.h2.UserRepositoryJPA;
import kg.devcats.coffee_shop.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender emailSender;

    private final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);
    private final UserRepositoryJPA userRepository;

    public EmailServiceImpl(UserRepositoryJPA userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void sendSimpleMail(String to, String subject, String content) {
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(content);

            emailSender.send(message);
        } catch (Exception e) {
            log.error("Error sending email", e);
        }

    }

    @Override
    public Boolean verifyToken(String token) {
        Optional<User> userOptional = userRepository.findByVerificationCode((token));
        if (!userOptional.isPresent()) {
            return false;
        }

        User user = userOptional.get();
        if(!user.getVerificationCodeExpiration().after(Timestamp.valueOf(LocalDateTime.now()))){

            user.setVerificationCode(UUID.randomUUID().toString());
            user.setVerificationCodeExpiration(Timestamp.valueOf(LocalDateTime.now().plusHours(1)));
            userRepository.save(user);

            sendSimpleMail(user.getEmail(), "Verification",
                    "Verification code: " + user.getVerificationCode().toString() +
                            "\n Link/Postman request: POST http://localhost:4445/api/auth/verify?token=" + user.getVerificationCode());
            throw new EmailTokenExpiredException("Email token expired");

        } else{
            user.setEmailVerified(true);
            user.setVerificationCodeExpiration(null);
            user.setVerificationCode(null);
            userRepository.save(user);
            return true;
        }


    }

}
