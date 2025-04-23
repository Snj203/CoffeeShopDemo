package kg.devcats.coffee_shop.controller.springMVC;

import jakarta.validation.Valid;
import kg.devcats.coffee_shop.entity.postgres.City;
import kg.devcats.coffee_shop.payload.city.request.CityRequestMVC;
import kg.devcats.coffee_shop.repository.postgres.CityRepositoryJPA;
import kg.devcats.coffee_shop.service.CityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/city")
public class CityControllerMVC {
    private final CityRepositoryJPA cityRepositoryJPA;
    private final CityService cityService;

    public CityControllerMVC(CityRepositoryJPA cityRepositoryJPA, CityService cityService) {
        this.cityRepositoryJPA = cityRepositoryJPA;
        this.cityService = cityService;
    }

    @GetMapping
    public String cityMainPage(){
        return "city/city_main_page_form";
    }

    @GetMapping("/add")
    public String getForm(Model model) {
        model.addAttribute("cityRequestMVC", new CityRequestMVC());
        return "city/city_form";
    }

    @PostMapping("/add")
    public String saveForm(
            @ModelAttribute @Valid CityRequestMVC request,
            BindingResult bindingResult
    ) {
        try{
            if(bindingResult.hasErrors()) {
                return "city/city_form";
            }

            if(cityService.save(request)){
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
            List<City> cityList = cityRepositoryJPA.findAll();
            if(cityList.isEmpty()) {
                return "general/general_empty_form";
            }
            model.addAttribute("cityList", cityList);
            return "city/city_list_form";
        } catch (Exception e){
            return "general/general_error_form";
        }
    }

    @GetMapping("/find/{idCity}")
    public String getFindByNameForm(
            Model model,
            @PathVariable("idCity") String id) {
        try{
            Optional<City> optionalCity = cityRepositoryJPA.findById(id);

            if(optionalCity.isPresent()) {
                City city = optionalCity.get();
                model.addAttribute("city", city);
                return "city/city_data_form";
            }
            return "general/general_empty_form";
        } catch (Exception e){
            return "general/general_error_form";
        }
    }

    @GetMapping("/delete")
    public String getDeleteForm() {
        return "city/city_delete_form";
    }

    @PostMapping("/delete")
    public String saveDeleteForm(
            @RequestParam("idCity") String id) {
        try{
            Optional<City> optionalCity = cityRepositoryJPA.findById(id);

            if(!optionalCity.isPresent()) {
                return "general/general_bad_request_form";
            }
            cityRepositoryJPA.deleteById(id);
            return "general/general_success_form";

        } catch(Exception e){
            return "general/general_error_form";
        }
    }
}
