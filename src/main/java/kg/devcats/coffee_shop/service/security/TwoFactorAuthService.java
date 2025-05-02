package kg.devcats.coffee_shop.service.security;

import kg.devcats.coffee_shop.entity.h2.User;
import kg.devcats.coffee_shop.repository.h2.UserRepositoryJPA;
import kg.devcats.coffee_shop.service.EmailService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Random;

@Service
public class TwoFactorAuthService {


    private final UserRepositoryJPA userRepository;
    private final EmailService emailService;

    public TwoFactorAuthService(UserRepositoryJPA userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    public void generateAndSend2faCode(User user) {
        String code = String.format("%06d", new Random().nextInt(999999));

        user.setTwoFactorCode(code);
        user.setTwoFactorCodeExpiration(Timestamp.valueOf(LocalDateTime.now().plusMinutes(5)));
        userRepository.save(user);

        String subject = ("2FA auth code");
        String message = ("Code: " + code + "\n\n expires in 5 minutes");
        emailService.sendSimpleMail(user.getEmail(), subject, message);
    }


    public boolean verify2faCode(User user, String code) {
        return code.equals(user.getTwoFactorCode()) &&
                !user.getTwoFactorCodeExpiration().before(Timestamp.valueOf(LocalDateTime.now()));
    }
}