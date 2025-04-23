package kg.devcats.coffee_shop.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kg.devcats.coffee_shop.repository.h2.UserRepositoryJPA;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final Long accessTokenExpiration;
    private final Long refreshTokenExpiration;
    private final AuthenticationManager authenticationManager;
    private final CustomJwtHelper customJwtHelper;
    private final UserRepositoryJPA userService;

    public CustomAuthenticationFilter(Long accessTokenExpiration, Long refreshTokenExpiration, AuthenticationConfiguration authenticationConfiguration, CustomJwtHelper customJwtHelper, UserRepositoryJPA userService) throws Exception {
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
        this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
        this.customJwtHelper = customJwtHelper;
        this.userService = userService;
    }


    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);

        return authenticationManager.authenticate(authenticationToken);
    }


    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authentication) throws IOException, ServletException {

        User user = (User) authentication.getPrincipal();

        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));

        String access_token = customJwtHelper.createToken(user.getUsername(), claims, accessTokenExpiration);
        String refresh_token = customJwtHelper.createToken(user.getUsername(), claims, refreshTokenExpiration);

        kg.devcats.coffee_shop.entity.h2.User dbuser = userService.findById(user.getUsername()).get();
        dbuser.setRefreshToken(refresh_token);
        userService.save(dbuser);

        Cookie jwtCookie = new Cookie("jwt", access_token);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(60 * 60);
        response.addCookie(jwtCookie);

        handleResponseSuccess(request,response, access_token, refresh_token);
    }

    protected void unsuccessfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {

        handleResponseFail(request,response);
    }

    private void handleResponseSuccess(HttpServletRequest request,HttpServletResponse response,String access_token,String refresh_token  ) throws IOException {
        String acceptHeader = request.getHeader("Accept");
        String userAgent = request.getHeader("User-Agent");

        if ((acceptHeader != null && acceptHeader.contains("text/html")) ||
                (userAgent != null && userAgent.contains("Mozilla"))){
            response.sendRedirect("/login-success");
        } else {
            Map<String, String> token = new HashMap<>();
            token.put("access_token", access_token);
            token.put("Expires in : ", accessTokenExpiration / 60 / 60 + " hours");
            token.put("refresh_token", refresh_token);
            token.put("Expires in : ", refreshTokenExpiration / 60 / 60 / 24 + " days");

            response.setContentType(APPLICATION_JSON_VALUE);
            response.addHeader("Authorization", "Bearer " + token);
            new ObjectMapper().writeValue(response.getOutputStream(), token);
        }
    }

    private void handleResponseFail(HttpServletRequest request,HttpServletResponse response ) throws IOException {
        String acceptHeader = request.getHeader("Accept");
        String userAgent = request.getHeader("User-Agent");

        if ((acceptHeader != null && acceptHeader.contains("text/html")) ||
                (userAgent != null && userAgent.contains("Mozilla"))){
            response.sendRedirect("/login-fail");
        } else {
            Map<String, String> token = new HashMap<>();
            token.put("Something wrong with username or password", "Authentication Failed");
            response.setContentType(APPLICATION_JSON_VALUE);
            response.addHeader("Authorization", "Bearer " + token);
            new ObjectMapper().writeValue(response.getOutputStream(), token);
        }
    }
}
