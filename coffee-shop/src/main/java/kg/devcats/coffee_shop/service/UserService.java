package kg.devcats.coffee_shop.service;

import kg.devcats.coffee_shop.payload.user.UserRequest;

public interface UserService {
    boolean register(UserRequest request);
}
