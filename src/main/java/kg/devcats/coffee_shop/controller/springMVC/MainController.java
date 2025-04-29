package kg.devcats.coffee_shop.controller.springMVC;

import kg.devcats.coffee_shop.payload.user.request.UserRequest;
import kg.devcats.coffee_shop.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {
    private final UserService userService;

    public MainController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String mainPage(){
        return "main_page_form";
    }

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
}
