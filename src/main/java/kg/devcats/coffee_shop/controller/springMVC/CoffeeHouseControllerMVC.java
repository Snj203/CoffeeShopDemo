package kg.devcats.coffee_shop.controller.springMVC;

import jakarta.validation.Valid;
import kg.devcats.coffee_shop.entity.postgres.CoffeeHouse;
import kg.devcats.coffee_shop.payload.coffeehouse.request.CoffeeHouseRequestMVC;
import kg.devcats.coffee_shop.repository.postgres.CoffeeHouseRepositoryJPA;
import kg.devcats.coffee_shop.service.mvc.CoffeeHouseServiceMVC;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/coffee-house")
public class CoffeeHouseControllerMVC {
    private final CoffeeHouseServiceMVC coffeeHouseService;
    private final CoffeeHouseRepositoryJPA coffeeHouseRepositoryJPA;

    public CoffeeHouseControllerMVC(CoffeeHouseServiceMVC coffeeHouseService, CoffeeHouseRepositoryJPA coffeeHouseRepositoryJPA) {
        this.coffeeHouseService = coffeeHouseService;
        this.coffeeHouseRepositoryJPA = coffeeHouseRepositoryJPA;
    }

    @GetMapping
    public String coffeeHouseMainPage(){
        return "cof_house/cof_house_main_page_form";
    }

    @GetMapping("/add")
    public String getForm(Model model) {
        model.addAttribute("coffeeHouseRequestMVC", new CoffeeHouseRequestMVC());
        return "cof_house/cof_house_form";
    }

    @PostMapping("/add")
    public String saveForm(
            @ModelAttribute @Valid CoffeeHouseRequestMVC request,
            BindingResult bindingResult
    ) {
        try{
            if(bindingResult.hasErrors()) {
                return "cof_house/cof_house_form";
            }

            if(coffeeHouseService.save(request)){
                return "general/general_success_form";
            } else{
                return "general/general_error_form";
            }
        } catch (Exception e) {
            return "general/general_error_form";
        }
    }

    @GetMapping("/find")
    public String getFindForm(Model model) {
        try{
            List<CoffeeHouse> coffeeHouseList = coffeeHouseRepositoryJPA.findAll();
            if(coffeeHouseList.isEmpty()) {
                return "general/general_empty_form";
            }
            model.addAttribute("coffeeHouseList", coffeeHouseList);
            return "cof_house/cof_house_list_form";
        } catch (Exception e){
            return "general/general_error_form";
        }
    }

    @GetMapping("/find/{idCoffeeHouse}")
    public String getFindByNameForm(
            Model model,
            @PathVariable("idCoffeeHouse") Long id) {
        try{
            Optional<CoffeeHouse> optionalCoffeeHouse = coffeeHouseRepositoryJPA.findById(id);

            if(optionalCoffeeHouse.isPresent()) {
                CoffeeHouse coffeeHouse = optionalCoffeeHouse.get();
                model.addAttribute("coffeeHouse", coffeeHouse);
                return "cof_house/cof_house_data_form";
            }
            return "general/general_empty_form";
        } catch (Exception e){
            return "general/general_error_form";
        }
    }

    @GetMapping("/update/{idCoffeeHouse}")
    public String getUpdateForm(
            @PathVariable Long idCoffeeHouse,
            Model model) {

        model.addAttribute("coffeeHouseRequestMVC", new CoffeeHouseRequestMVC());
        model.addAttribute("idCoffeeHouse", idCoffeeHouse);
        return "cof_house/cof_house_update_form";
    }

    @PostMapping("/update/{idCoffeeHouse}")
    public String saveUpdateForm(
            @PathVariable Long idCoffeeHouse,
            @ModelAttribute @Valid CoffeeHouseRequestMVC request,
            BindingResult bindingResult
    ){
        try{
            if (bindingResult.hasErrors()) {
                return "cof_house/cof_house_update_form";
            }

            if(coffeeHouseService.update(idCoffeeHouse,request)) {
                return "general/general_success_form";
            } else{
                return "general/general_error_form";
            }
        } catch (Exception e){
            return "general/general_error_form";
        }
    }

    @GetMapping("/delete")
    public String getDeleteForm() {
        return "cof_house/cof_house_delete_form";
    }

    @PostMapping("/delete")
    public String saveDeleteForm(
            @RequestParam("idCoffeeHouse") Long id) {
        try{
            Optional<CoffeeHouse> optionalCoffeeHouse = coffeeHouseRepositoryJPA.findById(id);

            if(!optionalCoffeeHouse.isPresent()) {
                return "general/general_bad_request_form";
            }
            coffeeHouseRepositoryJPA.deleteById(id);
            return "general/general_success_form";

        } catch(Exception e){
            return "general/general_success_form";
        }
    }
}
