package kg.devcats.coffee_shop.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import kg.devcats.coffee_shop.entity.Coffee;
import kg.devcats.coffee_shop.mapper.CoffeeMapper;
import kg.devcats.coffee_shop.payload.coffee.request.CoffeeRequest;
import kg.devcats.coffee_shop.payload.coffee.response.CoffeeResponse;
import kg.devcats.coffee_shop.service.api.CoffeeService;
import kg.devcats.coffee_shop.validation.MultipartFileSizeValid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/coffee")
public class CoffeeControllerAPI {
    private static final Logger log = LoggerFactory.getLogger(CoffeeControllerAPI.class);

    private final CoffeeService coffeeService;
    private final CoffeeMapper mapper;
    private final ObjectMapper objectMapper;

    public CoffeeControllerAPI(CoffeeService coffeeService, CoffeeMapper mapper, ObjectMapper objectMapper) {
        this.coffeeService = coffeeService;
        this.mapper = mapper;
        this.objectMapper = objectMapper;
    }

    @GetMapping
    public ResponseEntity<List<CoffeeResponse>> getCoffees(
            @RequestParam(required = false) String id) {
        try {
            List<CoffeeResponse> responses = new ArrayList<CoffeeResponse>();

            if (id == null)
                responses.addAll(mapper.toResponses(coffeeService.findAll()));
            else{
                Optional<Coffee> coffee = coffeeService.findById(id);
                if(coffee.isPresent()){
                    responses.add(mapper.toResponse(coffee.get()));
                }
            }
            if (responses.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(responses, HttpStatus.OK);
        } catch (Exception e) {
            log.error("getCoffees|CoffeeControllerAPI: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<String> createCoffee(
            @RequestPart @Valid String requestJson,
            @RequestPart @MultipartFileSizeValid MultipartFile photo
    ) {
        try{
            CoffeeRequest request = objectMapper.readValue(requestJson, CoffeeRequest.class);
            if(coffeeService.save(request, photo)){
                return new ResponseEntity<>("Coffee created", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Failed to create Coffee", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            log.error("createCoffee|CoffeeControllerAPI: " + e.getMessage());
            return new ResponseEntity<>("Failed to create Coffee, Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<String> updateCoffee(
            @RequestBody @Valid CoffeeRequest request,
            @RequestPart @MultipartFileSizeValid MultipartFile photo) {
        try{
            Optional<Coffee> coffee = coffeeService.findById(request.name());

            if(coffee.isPresent()) {

                coffeeService.update(request,photo);
                return new ResponseEntity<>("Coffee was updated successfully.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Cannot find Coffee", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("updateCoffee|CoffeeControllerAPI: " + e.getMessage());
            return new ResponseEntity<>("Cannot update Coffee.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping
    public ResponseEntity<String> deleteCoffee(
            @RequestParam String id) {
        try {
            if (coffeeService.deleteByIdCoffee(id)) {
                return new ResponseEntity<>("Coffee was deleted successfully.", HttpStatus.OK);
            }

            return new ResponseEntity<>("Cannot find Coffee", HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            log.error("deleteCoffee|CoffeeControllerAPI: " + e.getMessage());
            return new ResponseEntity<>("Cannot delete Coffee.", HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
}
