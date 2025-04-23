package kg.devcats.coffee_shop.controller.springMVC;

import jakarta.validation.Valid;
import kg.devcats.coffee_shop.entity.postgres.Merch;
import kg.devcats.coffee_shop.payload.merch.request.MerchReplenishRequest;
import kg.devcats.coffee_shop.payload.merch.request.MerchRequestMVC;
import kg.devcats.coffee_shop.repository.postgres.MerchRepositoryJPA;
import kg.devcats.coffee_shop.service.mvc.MerchServiceMVC;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/merch")
public class MerchControllerMVC {
    private final MerchServiceMVC merchService;
    private final MerchRepositoryJPA merchRepositoryJPA;

    public MerchControllerMVC(MerchServiceMVC merchService, MerchRepositoryJPA merchRepositoryJPA) {
        this.merchService = merchService;
        this.merchRepositoryJPA = merchRepositoryJPA;
    }

    @GetMapping
    public String merchMainPage(){
        return "merch/merch_main_page_form";
    }

    @GetMapping("/add")
    public String getForm(Model model) {
        model.addAttribute("merchRequestMVC", new MerchRequestMVC());
        return "merch/merch_form";
    }

    @PostMapping("/add")
    public String saveForm(
            @ModelAttribute @Valid MerchRequestMVC request,
            BindingResult bindingResult
    ) {
        try{
            if(bindingResult.hasErrors()) {
                return "merch/merch_form";
            }

            if(merchService.save(request)){
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
            List<Merch> merchList = merchRepositoryJPA.findAll();
            if(merchList.isEmpty()) {
                return "general/general_empty_form";
            }
            model.addAttribute("merchList", merchList);
            return "merch/merch_list_form";
        } catch (Exception e){
            return "general/general_error_form";
        }
    }

    @GetMapping("/find/{idMerch}")
    public String getFindByNameForm(
            Model model,
            @PathVariable("idMerch") Long id) {
        try{
            Optional<Merch> optionalMerch = merchRepositoryJPA.findById(id);

            if(optionalMerch.isPresent()) {
                Merch merch = optionalMerch.get();
                model.addAttribute("merch", merch);
                return "merch/merch_data_form";
            }
            return "general/general_empty_form";
        } catch (Exception e){
            return "general/general_error_form";
        }
    }

    @GetMapping("/update/{idMerch}")
    public String getUpdateForm(
            @PathVariable Long idMerch,
            Model model) {

        model.addAttribute("merchRequestMVC", new MerchRequestMVC());
        model.addAttribute("idMerch", idMerch);
        return "merch/merch_update_form";
    }

    @PostMapping("/update/{idMerch}")
    public String saveUpdateForm(
            @PathVariable Long idMerch,
            @ModelAttribute @Valid MerchRequestMVC request,
            BindingResult bindingResult
    ){
        try{
            if (bindingResult.hasErrors()) {
                return "merch/merch_update_form";
            }

            if(merchService.update(idMerch,request)) {
                return "general/general_success_form";
            } else{
                return "general/general_error_form";
            }
        } catch (Exception e){
            return "general/general_error_form";
        }
    }

    @GetMapping("/replenish/{idMerch}")
    public String getReplenishForm(
            @PathVariable Long idMerch,
            Model model) {

        model.addAttribute("merchReplenishRequest", new MerchReplenishRequest());
        model.addAttribute("idMerch", idMerch);
        return "merch/merch_replenish_form";
    }

    @PostMapping("/replenish/{idMerch}")
    public String saveReplenishForm(
            @PathVariable Long idMerch,
            @ModelAttribute @Valid MerchReplenishRequest request,
            BindingResult bindingResult
    ){
        try{
            if (bindingResult.hasErrors()) {
                return "merch/merch_replenish_form";
            }

            if(merchService.replenish(idMerch,request)) {
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
        return "merch/merch_delete_form";
    }

    @PostMapping("/delete")
    public String saveDeleteForm(
            @RequestParam("idMerch") Long id) {
        try{
            Optional<Merch> optionalMerch = merchRepositoryJPA.findById(id);

            if(!optionalMerch.isPresent()) {
                return "general/general_bad_request_form";
            }
            merchRepositoryJPA.deleteById(id);
            return "general/general_success_form";

        } catch(Exception e){
            return "general/general_success_form";
        }
    }
}
