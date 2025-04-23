package kg.devcats.coffee_shop.controller.api;

import jakarta.validation.Valid;
import kg.devcats.coffee_shop.entity.postgres.CoffeeHouse;
import kg.devcats.coffee_shop.mapper.CoffeeHouseMapper;
import kg.devcats.coffee_shop.payload.coffeehouse.request.CoffeeHouseRequest;
import kg.devcats.coffee_shop.payload.coffeehouse.response.CoffeeHouseResponse;
import kg.devcats.coffee_shop.service.api.CoffeeHouseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/coffee-house")
public class CoffeeHouseControllerAPI {
    private static final Logger log = LoggerFactory.getLogger(CoffeeHouseControllerAPI.class);

    private final CoffeeHouseService coffeeService;
    private final CoffeeHouseMapper mapper;

    public CoffeeHouseControllerAPI(CoffeeHouseService coffeeService, CoffeeHouseMapper mapper) {
        this.coffeeService = coffeeService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<CoffeeHouseResponse>> getCoffeeHouses(
            @RequestParam(required = false) Long id) {
        try {
            List<CoffeeHouseResponse> responses = new ArrayList<CoffeeHouseResponse>();

            if (id == null)
                responses.addAll(mapper.toResponses(coffeeService.findAll()));
            else{
                Optional<CoffeeHouse> coffeHouse = coffeeService.findById(id);
                if(coffeHouse.isPresent()){
                    responses.add(mapper.toResponse(coffeHouse.get()));
                }
            }
            if (responses.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(responses, HttpStatus.OK);
        } catch (Exception e) {
            log.error("getCoffeeHouses|CoffeeHouseControllerAPI: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<String> createCoffeeHouse(
            @RequestBody @Valid CoffeeHouseRequest request
    ) {
        try{
            if(coffeeService.save(request)){
                return new ResponseEntity<>("CoffeeHouse created", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Failed to create CoffeeHouse", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            log.error("createCoffeeHouse|CoffeeHouseControllerAPI: " + e.getMessage());
            return new ResponseEntity<>("Failed to create CoffeeHouse, Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<String> updateCoffeeHouse(
            @RequestBody @Valid CoffeeHouseRequest request,
            @RequestParam Long id) {
        try{
            Optional<CoffeeHouse> coffeeHouse = coffeeService.findById(id);

            if(coffeeHouse.isPresent()) {

                coffeeService.update(id, request);
                return new ResponseEntity<>("CoffeeHouse was updated successfully.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Cannot find CoffeeHouse", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("updateCoffeeHouse|CoffeeHouseControllerAPI: " + e.getMessage());
            return new ResponseEntity<>("Cannot update CoffeeHouse.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping
    public ResponseEntity<String> deleteCoffeeHouse(
            @RequestParam Long id) {
        try {
            if (coffeeService.deleteByIdCoffee(id)) {
                return new ResponseEntity<>("CoffeeHouse was deleted successfully.", HttpStatus.OK);
            }

            return new ResponseEntity<>("Cannot find CoffeeHouse", HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            log.error("deleteCoffeeHouse|CoffeeHouseControllerAPI: " + e.getMessage());
            return new ResponseEntity<>("Cannot delete CoffeeHouse.", HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
}
