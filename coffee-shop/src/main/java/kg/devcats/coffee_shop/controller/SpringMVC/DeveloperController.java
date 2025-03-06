package kg.devcats.coffee_shop.controller.SpringMVC;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import kg.test.demoall.entity.Developer;
import kg.test.demoall.payload.request.DeveloperRequestMVC;
import kg.test.demoall.repository.storage_to_file_system.StorageService;
import kg.test.demoall.service.DeveloperService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/developer")
public class DeveloperController {

    private final DeveloperService developerService;
    private final StorageService storageService;

    public DeveloperController(DeveloperService developerService, StorageService storageService) {
        this.developerService = developerService;
        this.storageService = storageService;
    }

    @GetMapping
    public void index(HttpServletResponse response) throws IOException {
        Map<String,String> token = new HashMap<>();
        String message = "Developer It Java KG";
        token.put("message", message);
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), token);
    }

    @GetMapping("/registration")
    public String getFormDeveloper(Model model) {
        model.addAttribute("developerRequestMVC", new DeveloperRequestMVC());
        return "developer_form";
    }

    @PostMapping("/registration")
    public String saveDataDeveloper(
            @ModelAttribute @Valid DeveloperRequestMVC request,
            BindingResult bindingResult
    ) {

        if (bindingResult.hasErrors()) {
            return "developer_form";
        }

        if(developerService.save(request)) {
            return "developer_result_form_registration";
        }
        return "developer_error_form_registration";
    }

    @GetMapping("/update/{idDeveloper}")
    public String getUpdateFormDeveloper(
            @PathVariable UUID idDeveloper,
            Model model) {

        model.addAttribute("idDeveloper", idDeveloper);

        model.addAttribute("developerRequestMVC", new DeveloperRequestMVC());
        return "developer_update_form";
    }

    @PostMapping("update/{idDeveloper}")
    public String updateDeveloper(
            @PathVariable UUID idDeveloper,
            @ModelAttribute @Valid DeveloperRequestMVC developerRequest,
            BindingResult bindingResult
    ){

        if (bindingResult.hasErrors()) {
            return "developer_update_form";
        }

        if(developerService.update(idDeveloper, developerRequest)){
            return "developer_result_form_registration";
        } else{
            return "developer_error_form_registration";
        }

    }

    @GetMapping("/find")
    public String findDeveloper(Model model) {
        List<Developer> developerList = developerService.findAll();
        if(developerList.isEmpty()) {
            return "developers_empty_list_data";
        }
        model.addAttribute("developers", developerList);
        return "developers_list_data";
    }

    @GetMapping("/find/{idDeveloper}")
    public String getDeveloper(
            @PathVariable("idDeveloper") UUID idDeveloper,
            Model model) {
        Optional<Developer> developerOptional = developerService.findById(idDeveloper);

        if(developerOptional.isPresent()) {
            Developer developer = developerOptional.get();
            model.addAttribute("developer", developer);
            return "developer_data";
        }
        model.addAttribute("developer", null);

        return "developer_data";
    }

    @GetMapping("/photo/{idDeveloper}")
    public void getDeveloperPhoto(@PathVariable UUID idDeveloper,
                               HttpServletResponse response) throws IOException {
        response.setContentType("image/png");

        Optional<Developer> developerOptional = developerService.findById(idDeveloper);
        if(developerOptional.isPresent()) {
            Developer developer = developerOptional.get();
            Resource resource = storageService.loadAsResource(developer.getPhotoDeveloper());

            InputStream inputStream = resource.getInputStream();
            IOUtils.copy(inputStream, response.getOutputStream());
        }
    }

    @GetMapping("/delete")
    public String deleteDeveloper() {
        return "developer_delete_form";
    }

    @PostMapping("/delete")
    public String deleteDeveloper(@RequestParam("uuid") String uuid) {
        try{
            int result = developerService.deleteByIdDeveloper(UUID.fromString(uuid));
            if(result == 1){
                return "general_success_form";
            } else{
                return "general_bad_request_form";
            }
        } catch(Exception e){
            return "general_error_form_";
        }
    }
}