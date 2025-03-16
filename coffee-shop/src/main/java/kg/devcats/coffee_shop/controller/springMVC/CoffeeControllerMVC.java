package kg.devcats.coffee_shop.controller.springMVC;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import kg.devcats.coffee_shop.entity.postgres.Coffee;
import kg.devcats.coffee_shop.payload.coffee.request.CoffeeBuyRequest;
import kg.devcats.coffee_shop.payload.coffee.request.CoffeeRequestMVC;
import kg.devcats.coffee_shop.repository.postgres.CoffeeServiceJPA;
import kg.devcats.coffee_shop.repository.storage_to_file_system.StorageService;
import kg.devcats.coffee_shop.service.mvc.CoffeeServiceMVC;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/coffee")
public class CoffeeControllerMVC {
    private static final Logger log = LoggerFactory.getLogger(CoffeeControllerMVC.class);
    private final CoffeeServiceMVC coffeeService;
    private final CoffeeServiceJPA coffeeServiceJPA;
    private final StorageService storageService;

    public CoffeeControllerMVC(CoffeeServiceMVC coffeeService, CoffeeServiceJPA coffeeServiceJPA, StorageService storageService) {
        this.coffeeService = coffeeService;
        this.coffeeServiceJPA = coffeeServiceJPA;
        this.storageService = storageService;
    }

    @GetMapping
    public String coffeeMainPage(){
        return "coffee/coffee_main_page_form";
    }

    @GetMapping("/add")
    public String getForm(Model model) {
    model.addAttribute("coffeeRequestMVC", new CoffeeRequestMVC());
    return "coffee/coffee_form";
}

    @PostMapping("/add")
    public String saveForm(
            @ModelAttribute @Valid CoffeeRequestMVC request,
            BindingResult bindingResult
    ) {
        try{
            if (bindingResult.hasErrors()) {
                return "coffee/coffee_form";
            }

            if(coffeeService.save(request)) {
                return "general/general_success_form";
            }else{
                return "general/general_error_form";
            }
        } catch (Exception e) {
            return "general/general_error_form";
        }
    }

    @GetMapping("/find")
    public String getFindForm(Model model) {
        try{
            List<Coffee> coffeeList = coffeeServiceJPA.findAll();
            if(coffeeList.isEmpty()) {
                return "general/general_empty_form";
            }
            model.addAttribute("coffeeList", coffeeList);
            return "coffee/coffee_list_form";
        } catch (Exception e){
            return "general/general_error_form";
        }
    }

    @GetMapping("/view")
    public String getViewForm(Model model) {
        try{
            List<Coffee> coffeeList = coffeeServiceJPA.findAll();
            if(coffeeList.isEmpty()) {
                return "general/general_empty_form";
            }
            model.addAttribute("coffeeList", coffeeList);
            return "coffee/coffee_view_form";
        } catch (Exception e){
            return "general/general_error_form";
        }
    }

    @GetMapping("/find/{cofName}")
    public String getFindByNameForm(
            Model model,
            @PathVariable("cofName") String cofName) {
        try{
            Optional<Coffee> optionalCoffee = coffeeServiceJPA.findById(cofName);

            if(optionalCoffee.isPresent()) {
                Coffee coffee = optionalCoffee.get();
                model.addAttribute("coffee", coffee);
                return "coffee/coffee_data_form";
            }
            return "general/general_empty_form";
        } catch (Exception e){
            return "general/general_error_form";
        }
    }

    @GetMapping("/update")
    public String getUpdateForm(
            Model model) {

        model.addAttribute("coffeeRequestMVC", new CoffeeRequestMVC());
        return "coffee/coffee_update_form";
    }

    @PostMapping("/update")
    public String saveUpdateForm(
            @ModelAttribute @Valid CoffeeRequestMVC coffee,
            BindingResult bindingResult
    ){
        try{
            if (bindingResult.hasErrors()) {
                return "coffee/coffee_update_form";
            }

            if(coffeeService.update(coffee)) {
                return "general/general_success_form";
            } else{
                return "general/general_error_form";
            }
        } catch (Exception e){
            return "general/general_error_form";
        }

    }

    @GetMapping("/photo/{cofName}")
    public void getDeveloperPhoto(
            @PathVariable("cofName") String cofName,
            HttpServletResponse response) throws IOException {
        response.setContentType("image/png");

        try{
            Optional<Coffee> optionalCoffee = coffeeServiceJPA.findById(cofName);
            if(optionalCoffee.isPresent()) {
                Coffee coffee = optionalCoffee.get();
                Resource resource = storageService.loadAsResource(coffee.getPhoto());

                InputStream inputStream = resource.getInputStream();
                IOUtils.copy(inputStream, response.getOutputStream());
            }
        } catch (Exception e){
            log.error("CoffeeControllerMVC.getPhoto()", e);
        }
    }

    @GetMapping("/delete")
    public String getDeleteForm() {
        return "coffee/coffee_delete_form";
    }

    @PostMapping("/delete")
    public String saveDeleteForm(
            @RequestParam("name") String name) {
        try{
            Optional<Coffee> optionalCoffee = coffeeServiceJPA.findById(name);

            if(!optionalCoffee.isPresent()) {
                return "general/general_bad_request_form";
            }
            coffeeServiceJPA.deleteById(name);
            return "general/general_success_form";

        } catch(Exception e){
            return "general/general_error_form";
        }
    }

    @GetMapping("/buy")
    public String getBuyForm(Model model) {
        model.addAttribute("coffeeBuyRequest", new CoffeeBuyRequest());
        return "coffee/coffee_buy_form";
    }

    @PostMapping("/buy")
    public String buyCoffee(
            @ModelAttribute @Valid CoffeeBuyRequest request,
            BindingResult bindingResult
    ){
        try{
            if (bindingResult.hasErrors()) {
                return "coffee/coffee_buy_form";
            }

            if(coffeeService.buy(request)) {
                return "general/general_success_form";
            } else{
                return "general/general_bad_request_form";
            }
        } catch (Exception e) {
            return "general/general_error_form";
        }
    }

}
