package kg.devcats.coffee_shop.controller.springMVC;

import jakarta.validation.Valid;
import kg.devcats.coffee_shop.entity.postgres.Supplier;
import kg.devcats.coffee_shop.payload.supplier.request.SupplierRequestMVC;
import kg.devcats.coffee_shop.repository.postgres.SupplierServiceJPA;
import kg.devcats.coffee_shop.service.mvc.SupplierServiceMVC;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/supplier")
public class SupplierControllerMVC {
    private final SupplierServiceJPA supplierServiceJPA;
    private final SupplierServiceMVC supplierService;

    public SupplierControllerMVC(SupplierServiceJPA supplierServiceJPA, SupplierServiceMVC supplierService) {
        this.supplierServiceJPA = supplierServiceJPA;
        this.supplierService = supplierService;
    }

    @GetMapping
    public String supplierMainPage(){
        return "supplier/sup_main_page_form";
    }

    @GetMapping("/add")
    public String getForm(Model model) {
        model.addAttribute("supplierRequestMVC", new SupplierRequestMVC());
        return "supplier/sup_form";
    }

    @PostMapping("/add")
    public String saveForm(
            @ModelAttribute @Valid SupplierRequestMVC request,
            BindingResult bindingResult
    ) {
        try{
            if(bindingResult.hasErrors()) {
                return "supplier/sup_form";
            }

            if(supplierService.save(request)){
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
            List<Supplier> supplierList = supplierServiceJPA.findAll();
            if(supplierList.isEmpty()) {
                return "general/general_empty_form";
            }
            model.addAttribute("supplierList", supplierList);
            return "supplier/sup_list_form";
        } catch (Exception e){
            return "general/general_error_form";
        }
    }

    @GetMapping("/find/{idSupplier}")
    public String getFindByNameForm(
            Model model,
            @PathVariable("idSupplier") Long id) {
        try{
            Optional<Supplier> optionalSupplier = supplierServiceJPA.findById(id);

            if(optionalSupplier.isPresent()) {
                Supplier supplier = optionalSupplier.get();
                model.addAttribute("supplier", supplier);
                return "supplier/sup_data_form";
            }
            return "general/general_empty_form";
        } catch (Exception e){
            return "general/general_error_form";
        }
    }

    @GetMapping("/update/{idSupplier}")
    public String getUpdateForm(
            @PathVariable Long idSupplier,
            Model model) {

        model.addAttribute("supplierRequestMVC", new SupplierRequestMVC());
        model.addAttribute("idSupplier", idSupplier);
        return "supplier/sup_update_form";
    }

    @PostMapping("/update/{idSupplier}")
    public String saveUpdateForm(
            @PathVariable Long idSupplier,
            @ModelAttribute @Valid SupplierRequestMVC request,
            BindingResult bindingResult
    ){
        try{
            if (bindingResult.hasErrors()) {
                return "supplier/sup_update_form";
            }

            if(supplierService.update(idSupplier,request)) {
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
        return "supplier/sup_delete_form";
    }

    @PostMapping("/delete")
    public String saveDeleteForm(
            @RequestParam("idSupplier") Long id) {
        try{
            Optional<Supplier> optionalSupplier = supplierServiceJPA.findById(id);

            if(!optionalSupplier.isPresent()) {
                return "general/general_bad_request_form";
            }
            supplierServiceJPA.deleteById(id);
            return "general/general_success_form";

        } catch(Exception e){
            return "general/general_success_form";
        }
    }
}
