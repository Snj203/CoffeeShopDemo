package kg.devcats.coffee_shop.controller.springMVC;

import jakarta.validation.Valid;
import kg.devcats.coffee_shop.entity.State;
import kg.devcats.coffee_shop.payload.state.request.StateRequestMVC;
import kg.devcats.coffee_shop.repository.jpa.StateServiceJPA;
import kg.devcats.coffee_shop.service.StateService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/state")
public class StateControllerMVC {
    private final StateServiceJPA stateServiceJPA;
    private final StateService stateService;

    public StateControllerMVC(StateServiceJPA stateServiceJPA, StateService stateService) {
        this.stateServiceJPA = stateServiceJPA;
        this.stateService = stateService;
    }

    @GetMapping
    public String stateMainPage(){
        return "state/state_main_page_form";
    }

    @GetMapping("/add")
    public String getForm(Model model) {
        model.addAttribute("stateRequestMVC", new StateRequestMVC());
        return "state/state_form";
    }

    @PostMapping("/add")
    public String saveForm(
            @ModelAttribute @Valid StateRequestMVC request,
            BindingResult bindingResult
    ) {
        try{
            if(bindingResult.hasErrors()) {
                return "state/state_form";
            }

            if(stateService.save(request)){
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
            List<State> stateList = stateServiceJPA.findAll();
            if(stateList.isEmpty()) {
                return "general/general_empty_form";
            }
            model.addAttribute("stateList", stateList);
            return "state/state_list_form";
        } catch (Exception e){
            return "general/general_error_form";
        }
    }

    @GetMapping("/find/{idState}")
    public String getFindByNameForm(
            Model model,
            @PathVariable("idState") String id) {
        try{
            Optional<State> optionalState = stateServiceJPA.findById(id);

            if(optionalState.isPresent()) {
                State state = optionalState.get();
                model.addAttribute("state", state);
                return "state/state_data_form";
            }
            return "general/general_empty_form";
        } catch (Exception e){
            return "general/general_error_form";
        }
    }

    @GetMapping("/delete")
    public String getDeleteForm() {
        return "state/state_delete_form";
    }

    @PostMapping("/delete")
    public String saveDeleteForm(
            @RequestParam("idState") String id) {
        try{
            Optional<State> optionalState = stateServiceJPA.findById(id);

            if(!optionalState.isPresent()) {
                return "general/general_bad_request_form";
            }
            stateServiceJPA.deleteById(id);
            return "general/general_success_form";

        } catch(Exception e){

            return "general/general_error_form";
        }
    }
}
