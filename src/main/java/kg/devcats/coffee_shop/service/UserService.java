package kg.devcats.coffee_shop.service;

import kg.devcats.coffee_shop.entity.h2.User;
import kg.devcats.coffee_shop.payload.user.request.UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface UserService {
    boolean register(UserRequest request);
    User register(OAuth2User user);
}
