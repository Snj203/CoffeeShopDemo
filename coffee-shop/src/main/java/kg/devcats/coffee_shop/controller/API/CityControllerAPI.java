package kg.devcats.coffee_shop.controller.API;

import jakarta.validation.Valid;
import kg.devcats.coffee_shop.entity.City;
import kg.devcats.coffee_shop.mapper.CityMapper;
import kg.devcats.coffee_shop.payload.city.request.CityRequest;
import kg.devcats.coffee_shop.payload.city.response.CityResponse;
import kg.devcats.coffee_shop.service.CityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/city")
public class CityControllerAPI {
    private static final Logger log = LoggerFactory.getLogger(CityControllerAPI.class);

    private final CityService cityService;
    private final CityMapper cityMapper;

    public CityControllerAPI(CityService cityService, CityMapper cityMapper) {
        this.cityService = cityService;
        this.cityMapper = cityMapper;
    }

    @GetMapping
    public ResponseEntity<List<CityResponse>> getCities(
            @RequestParam(required = false) String name) {
        try {
            List<CityResponse> responses = new ArrayList<CityResponse>();

            if (name == null) {
                responses.addAll(cityMapper.toResponses(cityService.findAll()));
            } else{
                Optional<City> city = cityService.findById(name);
                if(city.isPresent()){
                    responses.add(cityMapper.toResponse(city.get()));
                }
            }

            if (responses.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(responses, HttpStatus.OK);
        } catch (Exception e) {
            log.error("getCities|CityControllerAPI: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<String> createCity(
            @Valid @RequestBody CityRequest request
    ) {
        try{
            if(!cityService.save(request)){
                return new ResponseEntity<>("Failed to create City", HttpStatus.INTERNAL_SERVER_ERROR);
            } else{
                return new ResponseEntity<>("City created", HttpStatus.CREATED);
            }
        } catch (Exception e) {
            log.error("createCity|CityControllerAPI: " + e.getMessage());
            return new ResponseEntity<>("Failed to create City, Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteCity(
            @RequestParam String name) {
        try {
            if(cityService.deleteById(name)){
                return new ResponseEntity<>("City was deleted successfully.", HttpStatus.OK);
            }
            return new ResponseEntity<>("Cannot find City", HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            log.error("deleteCity|CityControllerAPI: " + e.getMessage());
            return new ResponseEntity<>("Cannot delete City.", HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
}
