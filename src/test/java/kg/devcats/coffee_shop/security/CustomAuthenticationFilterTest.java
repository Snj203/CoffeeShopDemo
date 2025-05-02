package kg.devcats.coffee_shop.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kg.devcats.coffee_shop.repository.h2.UserRepositoryJPA;
import kg.devcats.coffee_shop.security.filter.CustomAuthenticationFilter;
import kg.devcats.coffee_shop.security.component.CustomJwtHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomAuthenticationFilterTest {

    private CustomAuthenticationFilter filter;

    @Mock
    private AuthenticationManager authManager;

    @Mock
    private CustomJwtHelper jwtHelper;

    @Mock
    private UserRepositoryJPA userRepo;

    @Mock
    private AuthenticationConfiguration authConfig;

    @Mock
    private HttpServletResponse response;

    @BeforeEach
    void setUp() throws Exception {

        when(authConfig.getAuthenticationManager()).thenReturn(authManager);

        filter = new CustomAuthenticationFilter(
                3600L, // accessTokenExpiration
                86400L, // refreshTokenExpiration
                authConfig,
                jwtHelper,
                userRepo
        );
    }

    @Test
    void testAttemptAuthentication() {
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getParameter("username")).thenReturn("testuser");
        when(request.getParameter("password")).thenReturn("password");

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken("testuser", "password");

        when(authManager.authenticate(token)).thenReturn(mock(Authentication.class));

        Authentication result = filter.attemptAuthentication(request, response);
        assertNotNull(result);
    }

    @Test
    void testUnsuccessfulAuthentication_HTML() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getHeader("Accept")).thenReturn("text/html");

        filter.unsuccessfulAuthentication(request, response, mock(AuthenticationException.class));

        verify(response).sendRedirect("/login-fail");
    }
}