package kg.devcats.coffee_shop.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

public class CustomAuthorizationFilter extends OncePerRequestFilter {

    private final CustomJwtHelper customJwtHelper;
    private final Logger log = LoggerFactory.getLogger(CustomAuthorizationFilter.class);

    public CustomAuthorizationFilter(CustomJwtHelper customJwtHelper) {
        this.customJwtHelper = customJwtHelper;
    }

    @Override
    public void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws IOException, ServletException {

        String token = null;
        log.info(request.getRequestURI());

        if(request.getServletPath().equals("/")
                || request.getServletPath().equals("/login-fail")
                || request.getServletPath().equals("/registration")
                || request.getServletPath().equals("/not-enough-permissions")
                || request.getServletPath().startsWith("/css")
                || request.getServletPath().startsWith("/login")
                || request.getServletPath().startsWith("/verify")
                || request.getServletPath().startsWith("/oauth2")
                || request.getServletPath().startsWith("/api/auth")
                || request.getServletPath().startsWith("/h2-console")
                || request.getServletPath().startsWith("/images")) {

            filterChain.doFilter(request, response);
            return;

        } else if(request.getServletPath().startsWith("/api")) {
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                token = authorizationHeader.substring("Bearer ".length());
            } else{
                response.setStatus(HttpStatus.FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", "Access denied. Token is missing.");
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
                return;
            }
        } else if (request.getCookies() != null){
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("jwt")) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        if(token != null) {
            try {
                Map<String, Object> claims = customJwtHelper.verifyToken(token);
                String username = (String) claims.get("sub");
                List<String> roles = (List<String>) claims.get("roles");

                Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                for (String role : roles) {
                    authorities.add(new SimpleGrantedAuthority(role));
                }

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                filterChain.doFilter(request, response);
                return;
            } catch (Exception e) {
                response.setHeader("error", e.getMessage());
                response.setStatus(HttpStatus.FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", e.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
                return;

            }
        }
        if(request.getServletPath().startsWith("/api" )){
            filterChain.doFilter(request, response);
            return;
        } else if(SecurityContextHolder.getContext().getAuthentication() == null) {
            filterChain.doFilter(request, response);
            return;
        }

        response.sendRedirect("/not-enough-permissions");
        return;
    }
}
