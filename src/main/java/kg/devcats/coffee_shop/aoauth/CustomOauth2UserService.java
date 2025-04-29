package kg.devcats.coffee_shop.aoauth;

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
    private final CustomJwtHelper customJwtHelper;
    private final UserService userService;
    private final Logger log = LoggerFactory.getLogger(CustomOauth2UserService.class);

    @Value("${jwt.accessToken.expiration}")
    private Long accessTokenExpiration;

    public CustomOauth2UserService(UserRepositoryJPA userRepositoryJPA, CustomJwtHelper customJwtHelper, @Lazy UserService userService) {
        this.userRepositoryJPA = userRepositoryJPA;
        this.customJwtHelper = customJwtHelper;
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

            List<GrantedAuthority> authorities = new ArrayList<>();
            for(Authority authority : user.getAuthorities()){
                authorities.add(new SimpleGrantedAuthority(authority.getAuthority()));
            }

            Map<String, Object> claims = new HashMap<>();
            claims.put("roles", authorities.stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList()));

            String access_token = customJwtHelper.createToken(user.getUsername(), claims, accessTokenExpiration);

            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();

            Cookie jwtCookie = new Cookie("jwt", access_token);
                        jwtCookie.setHttpOnly(true);
                        jwtCookie.setPath("/");
                        jwtCookie.setMaxAge(3600);
                        response.addCookie(jwtCookie);
        }

        // Return a custom user implementation
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                attributes,
                "email" // or whatever your "nameAttributeKey" is
        );
    }

}
