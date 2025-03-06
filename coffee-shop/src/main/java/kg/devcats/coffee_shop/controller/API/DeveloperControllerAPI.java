package kg.devcats.coffee_shop.controller.API;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kg.test.demoall.payload.request.DeveloperRequest;
import kg.test.demoall.payload.response.DeveloperResponse;
import kg.test.demoall.service.DeveloperServiceAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/developer")
public class DeveloperControllerAPI {

    private static final Logger log = LoggerFactory.getLogger(DeveloperControllerAPI.class);
    private final DeveloperServiceAPI developerServiceAPI;

    public DeveloperControllerAPI(DeveloperServiceAPI developerServiceAPI) {
        this.developerServiceAPI = developerServiceAPI;
    }


    @GetMapping
    public ResponseEntity<List<DeveloperResponse>> getDeveloper(
            @RequestParam(required = false) UUID id) {
        try {
            List<DeveloperResponse> responses = new ArrayList<DeveloperResponse>();

            if (id == null)
                responses.addAll(developerServiceAPI.findAll());
            else
                responses.addAll(developerServiceAPI.findById(id));

            if (responses.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(responses, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping
    public ResponseEntity<String> createDeveloper(
            @Valid @RequestBody DeveloperRequest developerRequest,
            @RequestParam @NotNull MultipartFile photo
    ) {
        try{
            if(developerServiceAPI.createDeveloper(developerRequest, photo)){
                return new ResponseEntity<>("Developer created", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Failed to create developer", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create developer, Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping
    public ResponseEntity<String> updateDeveloper(
            @Valid @RequestBody DeveloperRequest developerRequest,
            @RequestParam @NotNull @NotBlank UUID id) {
        Optional<DeveloperRequest> _request = developerServiceAPI.findByIdDeveloper(id);

        if(_request.isPresent()) {

            developerServiceAPI.updateDeveloper(id, developerRequest);
            return new ResponseEntity<>("Developer was updated successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cannot find Developer with idDeveloper = " + id, HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping
    public ResponseEntity<String> deleteDeveloper(
            @RequestParam @NotNull UUID id) {
        try {
            if (developerServiceAPI.deleteDeveloperById(id)) {
                return new ResponseEntity<>("Developer was deleted successfully.", HttpStatus.OK);
            }

            return new ResponseEntity<>("Cannot find Developer with id=" + id, HttpStatus.OK);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Cannot delete developer.", HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
}
