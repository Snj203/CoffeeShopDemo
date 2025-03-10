package kg.devcats.coffee_shop.repository.springMVC;

import kg.devcats.coffee_shop.entity.h2.Authority;
import kg.devcats.coffee_shop.entity.h2.User;
import kg.devcats.coffee_shop.payload.user.request.UserRequest;
import kg.devcats.coffee_shop.repository.h2.UserServiceJPA;
import kg.devcats.coffee_shop.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository
public class UserRepositoryMVC implements UserService {
    private final UserServiceJPA userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserRepositoryMVC(UserServiceJPA userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public boolean register(UserRequest request) {
        User user = new User();
        user.setUsername(request.userName());
        user.setPassword(bCryptPasswordEncoder.encode(request.password()));

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

        userService.save(user);
        return true;
    }
}
