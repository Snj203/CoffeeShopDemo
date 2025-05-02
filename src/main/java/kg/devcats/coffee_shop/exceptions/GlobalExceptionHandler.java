package kg.devcats.coffee_shop.exceptions;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RateLimitExceededException.class)
    public void handleRateLimitException(HttpServletResponse response, RateLimitExceededException e) throws IOException {

        response.sendRedirect("/too-many-requests");
    }
}