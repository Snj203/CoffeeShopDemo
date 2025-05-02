package kg.devcats.coffee_shop.service.security;

import jakarta.servlet.http.Cookie;
import kg.devcats.coffee_shop.entity.h2.Authority;
import kg.devcats.coffee_shop.entity.h2.User;
import kg.devcats.coffee_shop.security.component.CustomJwtHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CookieService {

    private final CustomJwtHelper customJwtHelper;

    @Value("${jwt.accessToken.expiration}")
    private Long accessTokenExpiration;

    public CookieService(CustomJwtHelper customJwtHelper) {
        this.customJwtHelper = customJwtHelper;
    }


    public Cookie generateCoockie(User user){

        List<GrantedAuthority> authorities = new ArrayList<>();
        for(Authority authority : user.getAuthorities()){
            authorities.add(new SimpleGrantedAuthority(authority.getAuthority()));
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));

        String access_token = customJwtHelper.createToken(user.getUsername(), claims, accessTokenExpiration);

        Cookie jwtCookie = new Cookie("jwt", access_token);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(3600);

        return jwtCookie;
    }
}
