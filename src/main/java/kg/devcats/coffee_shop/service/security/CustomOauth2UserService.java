package kg.devcats.coffee_shop.service.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import kg.devcats.coffee_shop.entity.h2.Authority;
import kg.devcats.coffee_shop.entity.h2.User;
import kg.devcats.coffee_shop.repository.h2.UserRepositoryJPA;
import kg.devcats.coffee_shop.security.filter.CustomJwtHelper;
import kg.devcats.coffee_shop.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustomOauth2UserService extends DefaultOAuth2UserService {

    private final DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
    private final UserRepositoryJPA userRepositoryJPA;
    private final UserService userService;
    private final Logger log = LoggerFactory.getLogger(CustomOauth2UserService.class);
    private final CookieService cookieService;


    public CustomOauth2UserService(UserRepositoryJPA userRepositoryJPA, @Lazy UserService userService, CookieService cookieService) {
        this.userRepositoryJPA = userRepositoryJPA;
        this.cookieService = cookieService;
        this.userService = userService;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        Map<String, Object> attributes = new HashMap<>(oAuth2User.getAttributes());

        if ("google".equals(registrationId)) {

            Optional<User> userOptional = userRepositoryJPA.findByEmail(attributes.get("email").toString());
            User user;

            if(userOptional.isEmpty()){
                user = userService.register(oAuth2User);
            } else{
                user = userOptional.get();
            }

            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
            response.addCookie(cookieService.generateCoockie(user));
        }

        // Return a custom user implementation
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                attributes,
                "email" // or whatever your "nameAttributeKey" is
        );
    }

}


