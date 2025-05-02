package kg.devcats.coffee_shop.controller.springMVC;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kg.devcats.coffee_shop.annotations.rate_limiting.RateLimiter;
import kg.devcats.coffee_shop.entity.h2.User;
import kg.devcats.coffee_shop.payload.user.request.UserRequest;
import kg.devcats.coffee_shop.repository.h2.UserRepositoryJPA;
import kg.devcats.coffee_shop.security.component.CustomJwtHelper;
import kg.devcats.coffee_shop.service.UserService;
import kg.devcats.coffee_shop.service.security.CookieService;
import kg.devcats.coffee_shop.service.security.TwoFactorAuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/")
public class MainController {
    private final UserService userService;
    private final UserRepositoryJPA userRepositoryJPA;
    private final TwoFactorAuthService twoFactorAuthService;
    private final CookieService cookieService;

    public MainController(UserService userService, UserRepositoryJPA userRepositoryJPA, TwoFactorAuthService twoFactorAuthService, CustomJwtHelper customJwtHelper, CookieService cookieService) {
        this.userService = userService;
        this.userRepositoryJPA = userRepositoryJPA;
        this.twoFactorAuthService = twoFactorAuthService;
        this.cookieService = cookieService;
    }

    @GetMapping
    public String mainPage(){
        return "main_page_form";
    }

    @RateLimiter(upperBound = 2, refillTokens = 2, duration = 120L)
    @GetMapping("/login")
    public String login() {
        return "login_form";
    }

    @GetMapping("/login-success")
    public String loginSuccess() {
        return "login_success_form";
    }

    @GetMapping("/login-fail")
    public String loginFail() {
        return "login_fail_form";
    }

    @GetMapping("/logout")
    public String logout() {
        return "logout_form";
    }

    @GetMapping("/not-enough-permissions")
    public String forbiddenPermissions() {
        return "not_enough_permissions";
    }

    @GetMapping("/registration")
    public String getRegister() {
        return "registration_form";
    }

    @PostMapping("/registration")
    public String saveUser(@ModelAttribute UserRequest developerClient) {
        try {
            if(userService.register(developerClient)){
                return "general/general_success_form";
            } else{
                return "general/general_bad_request_form";
            }
        } catch (Exception e) {
            return "general/general_error_form";
        }
    }

    @GetMapping("/verify/2fa")
    public String showVerify2faPage() {
        return "verify-2fa";
    }

    @PostMapping("/verify/2fa")
    public String verify2faCode(@RequestParam String code, Model model, HttpServletResponse response, HttpServletRequest request) {
        Optional<User> userOptional = userRepositoryJPA.findById((String)request.getSession().getAttribute("tmpusrnm"));
        if(userOptional.isEmpty()){
            model.addAttribute("error", "Invalid user");
            return "verify-2fa";
        }
        User user = userOptional.get();

        if (twoFactorAuthService.verify2faCode(user, code)) {
            user.setTwoFactorCode(null);
            user.setTwoFactorCodeExpiration(null);
            userRepositoryJPA.save(user);

            response.addCookie(cookieService.generateCoockie(user));

            return "redirect:/login-success";
        } else {
            model.addAttribute("error", "Invalid code");
            return "verify-2fa";
        }
    }

    @PostMapping("/resend-2fa")
    public String resend2faCode(HttpServletRequest request) {
        User user = userRepositoryJPA.findById((String)request.getSession().getAttribute("tmpusrnm")).get();
        twoFactorAuthService.generateAndSend2faCode(user);
        return "redirect:/verify/2fa?resent=true";
    }

    @GetMapping("/too-many-requests")
    public String tooManuRequests() {
        return "too_many_requests";
    }
}
