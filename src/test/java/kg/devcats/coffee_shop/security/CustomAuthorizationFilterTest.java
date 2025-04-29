package kg.devcats.coffee_shop.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kg.devcats.coffee_shop.security.filter.CustomAuthorizationFilter;
import kg.devcats.coffee_shop.security.filter.CustomJwtHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.DelegatingServletOutputStream;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomAuthorizationFilterTest {

    private CustomJwtHelper jwtHelper;
    private CustomAuthorizationFilter filter;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain filterChain;

    @BeforeEach
    void setUp() {
        jwtHelper = mock(CustomJwtHelper.class);
        filter = new CustomAuthorizationFilter(jwtHelper);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        filterChain = mock(FilterChain.class);
        SecurityContextHolder.clearContext();
    }

    @Test
    void shouldSkipPathsWithoutAuth() throws Exception {
        when(request.getServletPath()).thenReturn("/");
        filter.doFilterInternal(request, response, filterChain);
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void shouldAllowAccessWithValidBearerToken() throws Exception {
        when(request.getServletPath()).thenReturn("/api/test");
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer validtoken");
        when(jwtHelper.verifyToken("validtoken")).thenReturn(Map.of(
                "sub", "testuser",
                "roles", List.of("ROLE_USER")
        ));

        filter.doFilterInternal(request, response, filterChain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals("testuser", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void shouldReturnErrorForMissingToken() throws Exception {
        when(request.getServletPath()).thenReturn("/api/test");
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(null);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        when(response.getOutputStream()).thenReturn(new DelegatingServletOutputStream(outputStream));

        filter.doFilterInternal(request, response, filterChain);

        verify(response).setStatus(403);
        String json = outputStream.toString();
        assertTrue(json.contains("Token is missing"));
    }

    @Test
    void shouldAuthorizeWithValidJwtCookie() throws Exception {
        when(request.getServletPath()).thenReturn("/protected");
        when(request.getCookies()).thenReturn(new Cookie[]{
                new Cookie("jwt", "validcookie")
        });

        when(jwtHelper.verifyToken("validcookie")).thenReturn(Map.of(
                "sub", "cookieUser",
                "roles", List.of("ROLE_ADMIN")
        ));

        filter.doFilterInternal(request, response, filterChain);

        assertEquals("cookieUser", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void shouldReturnErrorIfTokenInvalid() throws Exception {
        when(request.getServletPath()).thenReturn("/protected");
        when(request.getCookies()).thenReturn(new Cookie[]{
                new Cookie("jwt", "invalid")
        });

        when(jwtHelper.verifyToken("invalid")).thenThrow(new RuntimeException("Invalid token"));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        when(response.getOutputStream()).thenReturn(new DelegatingServletOutputStream(outputStream));

        filter.doFilterInternal(request, response, filterChain);

        verify(response).setStatus(403);
        assertTrue(outputStream.toString().contains("Invalid token"));
    }
    
}