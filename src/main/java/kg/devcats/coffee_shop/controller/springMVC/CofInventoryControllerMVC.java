package kg.devcats.coffee_shop.controller.springMVC;

import jakarta.validation.Valid;
import kg.devcats.coffee_shop.entity.postgres.CofInventory;
import kg.devcats.coffee_shop.payload.cof_inventory.request.CofInventoryModelRequest;
import kg.devcats.coffee_shop.payload.cof_inventory.request.CofInventoryReplenishRequest;
import kg.devcats.coffee_shop.service.CofInventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/storage")
public class CofInventoryControllerMVC {
    private static final Logger log = LoggerFactory.getLogger(CofInventoryControllerMVC.class);
    private final CofInventoryService cofInventoryService;

    public CofInventoryControllerMVC(CofInventoryService cofInventoryService) {
        this.cofInventoryService = cofInventoryService;
    }

    @GetMapping
    public String cofInventoryMainPage(){
        return "cof_inventory/cof_inventory_main_page_form";
    }

    @GetMapping("/add")
    public String getForm(Model model) {
        model.addAttribute("cofInventoryUpdateRequest", new CofInventoryModelRequest());
        return "cof_inventory/cof_inventory_form";
    }

    @PostMapping("/add")
    public String saveForm(
            @ModelAttribute @Valid CofInventoryModelRequest request,
            BindingResult bindingResult
    ) {
        try{
            if(bindingResult.hasErrors()) {
                return "cof_inventory/cof_inventory_form";
            }

            if(cofInventoryService.save(request)){
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
            List<CofInventory> cofInventoryList = cofInventoryService.findAll();
            if(cofInventoryList.isEmpty()) {
                return "general/general_empty_form";
            }
            model.addAttribute("cofInventoryList", cofInventoryList);
            return "cof_inventory/cof_inventory_list_form";
        } catch (Exception e){
            return "general/general_error_form";
        }
    }

    @GetMapping("/find/{idStorage}")
    public String getFindByNameForm(
            Model model,
            @PathVariable("idStorage") Long id) {
        try{
            Optional<CofInventory> optionalCofInventory = cofInventoryService.findById(id);

            if(optionalCofInventory.isPresent()) {
                CofInventory cofInventory = optionalCofInventory.get();
                model.addAttribute("cofInventory", cofInventory);
                return "cof_inventory/cof_inventory_data_form";
            }
            return "general/general_empty_form";
        } catch (Exception e){
            return "general/general_error_form";
        }
    }

    @GetMapping("/update/{idStorage}")
    public String getUpdateForm(
            @PathVariable Long idStorage,
            Model model) {

        model.addAttribute("cofInventoryUpdateRequest", new CofInventoryModelRequest());
        model.addAttribute("idStorage", idStorage);
        return "cof_inventory/cof_inventory_update_form";
    }

    @PostMapping("/update/{idStorage}")
    public String saveUpdateForm(
            @PathVariable Long idStorage,
            @ModelAttribute @Valid CofInventoryModelRequest request,
            BindingResult bindingResult
    ){
        try{
            if (bindingResult.hasErrors()) {
                return "cof_inventory/cof_inventory_update_form";
            }

            if(cofInventoryService.update(idStorage,request)) {
                return "general/general_success_form";
            } else{
                return "general/general_error_form";
            }
        } catch (Exception e){
            return "general/general_error_form";
        }
    }

    @GetMapping("/replenish/{idStorage}")
    public String getReplenishForm(
            @PathVariable Long idStorage,
            Model model) {

        model.addAttribute("cofInventoryReplenishRequest", new CofInventoryReplenishRequest());
        model.addAttribute("idStorage", idStorage);
        return "cof_inventory/cof_inventory_replenish_form";
    }

    @PostMapping("/replenish/{idStorage}")
    public String saveReplenishForm(
            @PathVariable Long idStorage,
            @ModelAttribute @Valid CofInventoryReplenishRequest request,
            BindingResult bindingResult
    ){
        try{
            if (bindingResult.hasErrors()) {
                return "cof_inventory/cof_inventory_replenish_form";
            }

            if(cofInventoryService.replenish(idStorage,request)) {
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
        return "cof_inventory/cof_inventory_delete_form";
    }

    @PostMapping("/delete")
    public String saveDeleteForm(
            @RequestParam("idCofInventory") Long id) {
        try{
            Optional<CofInventory> optionalCofInventory = cofInventoryService.findById(id);

            if(!optionalCofInventory.isPresent()) {
                return "general/general_bad_request_form";
            }
            cofInventoryService.deleteById(id);
            return "general/general_success_form";

        } catch(Exception e){
            return "general/general_success_form";
        }
    }

}
