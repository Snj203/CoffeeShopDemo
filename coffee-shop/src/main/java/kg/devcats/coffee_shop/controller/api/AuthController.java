package kg.devcats.coffee_shop.controller.api;

import kg.devcats.coffee_shop.filter.CustomJwtHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final CustomJwtHelper jwtHelper;
    private final Logger log = LoggerFactory.getLogger(AuthController.class);

    public AuthController(CustomJwtHelper jwtHelper) {
        this.jwtHelper = jwtHelper;
    }

    @PostMapping("/refresh")
    public ResponseEntity<Map<String,String>> refreshToken(@RequestParam String refreshToken) {
        try {
            return new ResponseEntity<>(jwtHelper.validateToken(refreshToken), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error while refresh: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
