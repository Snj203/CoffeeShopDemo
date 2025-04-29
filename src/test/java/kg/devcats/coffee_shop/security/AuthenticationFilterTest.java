package kg.devcats.coffee_shop.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kg.devcats.coffee_shop.entity.h2.User;
import kg.devcats.coffee_shop.repository.h2.UserRepositoryJPA;
import kg.devcats.coffee_shop.security.filter.CustomAuthenticationFilter;
import kg.devcats.coffee_shop.security.filter.CustomJwtHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationFilterTest {

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

        when(response.getOutputStream()).thenReturn(new ServletOutputStream() {
            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setWriteListener(WriteListener writeListener) {

            }

            @Override
            public void write(int b) throws IOException {

            }
        });

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
    void testSuccessfulAuthentication() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        FilterChain chain = mock(FilterChain.class);

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                "testuser", "pass",
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );

        Authentication auth = mock(Authentication.class);
        when(auth.getPrincipal()).thenReturn(userDetails);

        when(jwtHelper.createToken(eq("testuser"), any(), eq(3600L))).thenReturn("accessToken");
        when(jwtHelper.createToken(eq("testuser"), any(), eq(86400L))).thenReturn("refreshToken");

        User dbUser = new User();
        when(userRepo.findById("testuser")).thenReturn(Optional.of(dbUser));

        filter.successfulAuthentication(request, response, chain, auth);

        verify(userRepo).save(dbUser);
        assertEquals("refreshToken", dbUser.getRefreshToken());
        verify(response).addCookie(any(Cookie.class));
    }

    @Test
    void testUnsuccessfulAuthentication_JSON() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getHeader("Accept")).thenReturn("application/json");

        filter.unsuccessfulAuthentication(request, response, mock(AuthenticationException.class));

        verify(response).setContentType("application/json");
    }


}