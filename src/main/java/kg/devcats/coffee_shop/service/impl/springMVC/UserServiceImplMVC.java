package kg.devcats.coffee_shop.service.impl.springMVC;

import kg.devcats.coffee_shop.entity.h2.Authority;
import kg.devcats.coffee_shop.entity.h2.User;
import kg.devcats.coffee_shop.payload.user.request.UserRequest;
import kg.devcats.coffee_shop.repository.h2.UserRepositoryJPA;
import kg.devcats.coffee_shop.service.EmailService;
import kg.devcats.coffee_shop.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Repository
public class UserServiceImplMVC implements UserService {
    private final UserRepositoryJPA userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailService emailService;

    public UserServiceImplMVC(UserRepositoryJPA userService, BCryptPasswordEncoder bCryptPasswordEncoder, EmailService emailService) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.emailService = emailService;
    }

    @Override
    public boolean register(UserRequest request) {
        User user = new User(request.userName());
        user.setPassword(bCryptPasswordEncoder.encode(request.password()));
        user.setEmail(request.email());
        user.setEmailVerified(false);

        Set<Authority> authorities = new HashSet<>();
        Authority authority;
        for(String role : request.role()){
            authority = new Authority();
            authority.setUsername(request.userName());
            authority.setAuthority(role);
            authority.setUser(user);
            authorities.add(authority);
        }
        user.setAuthorities(authorities);
        user.setEnabled(true);
        user.setTwoFactorAuthEnabled(false);

        user.setVerificationCode(UUID.randomUUID().toString());
        user.setVerificationCodeExpiration(Timestamp.valueOf(LocalDateTime.now().plusHours(2)));

        userService.save(user);

        emailService.sendSimpleMail(request.email(), "Verification",
                "Verification code: " + user.getVerificationCode().toString() +
                        "\n Link/Postman request: POST http://localhost:4445/api/auth/verify?token=" + user.getVerificationCode());
        return true;
    }

    @Override
    public User register(OAuth2User oauthUser) {
        User user = new User(oauthUser.getAttribute("given_name") + " " + oauthUser.getAttribute("family_name"));
        UUID password = UUID.randomUUID();
        String message = "Password: " + password;
        user.setPassword(bCryptPasswordEncoder.encode(password.toString()));
        user.setEmail(oauthUser.getAttribute("email"));
        if(oauthUser.getAttribute("email_verified") != null && ((Boolean)oauthUser.getAttribute("email_verified"))){
            user.setEmailVerified(true);
        } else{
            user.setEmailVerified(false);
            user.setVerificationCode(UUID.randomUUID().toString());
            user.setVerificationCodeExpiration(Timestamp.valueOf(LocalDateTime.now().plusHours(2)));
            message = message +
                    "\n Verification code: " + user.getVerificationCode().toString() +
                    "\n Link/Postman request: POST http://localhost:4445/api/auth/verify?token=" + user.getVerificationCode();
        }

        Set<Authority> authorities = new HashSet<>();
        Authority authority;
        authority = new Authority();
        authority.setUsername(user.getUsername());
        authority.setAuthority("ROLE_USER");
        authority.setUser(user);
        authorities.add(authority);
        user.setAuthorities(authorities);
        user.setTwoFactorAuthEnabled(false);

        user.setEnabled(true);

        userService.save(user);

        emailService.sendSimpleMail(user.getEmail(), "Verification",
                message);
        return user;
    }
}
