package kg.devcats.coffee_shop.controller.api;

import kg.devcats.coffee_shop.exceptions.EmailTokenExpiredException;
import kg.devcats.coffee_shop.security.filter.CustomJwtHelper;
import kg.devcats.coffee_shop.service.EmailService;
import kg.devcats.coffee_shop.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final CustomJwtHelper jwtHelper;
    private final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final UserService userService;
    private final EmailService emailService;

    public AuthController(CustomJwtHelper jwtHelper, UserService userService, EmailService emailService) {
        this.jwtHelper = jwtHelper;
        this.userService = userService;
        this.emailService = emailService;
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

    @PostMapping("/verify")
    public ResponseEntity<String> verifyUser(@RequestParam String token) {
        try{
            if(emailService.verifyToken(token)){
                return new ResponseEntity<>("Verified successfully", HttpStatus.OK);
            } else{
                return new ResponseEntity<>("Token doesn't exist ", HttpStatus.UNAUTHORIZED);
            }
        } catch (EmailTokenExpiredException etee) {
            return new ResponseEntity<>("Email token expired, new code was sent via email", HttpStatus.UNAUTHORIZED);
        } catch (Exception e){
            log.error("Error while verify: " + e.getMessage());
            return new ResponseEntity<>("Errror while verifying token", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
