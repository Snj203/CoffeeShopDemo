package kg.devcats.coffee_shop.controller.api;

import jakarta.validation.Valid;
import kg.devcats.coffee_shop.entity.postgres.CofInventory;
import kg.devcats.coffee_shop.mapper.CofInventoryMapper;
import kg.devcats.coffee_shop.payload.cof_inventory.request.CofInventoryReplenishRequest;
import kg.devcats.coffee_shop.payload.cof_inventory.request.CofInventoryRequest;
import kg.devcats.coffee_shop.payload.cof_inventory.request.CofInventoryModelRequest;
import kg.devcats.coffee_shop.payload.cof_inventory.response.CofInventoryResponse;
import kg.devcats.coffee_shop.service.CofInventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cof-inventory")
public class CofInventoryControllerAPI {
    private static final Logger log = LoggerFactory.getLogger(CofInventoryControllerAPI.class);

    private final CofInventoryService cofInventoryService;
    private final CofInventoryMapper mapper;

    public CofInventoryControllerAPI(CofInventoryService cofInventoryService, CofInventoryMapper mapper) {
        this.cofInventoryService = cofInventoryService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<CofInventoryResponse>> getCofInventories(
            @RequestParam(required = false) Long id) {
        try {
            List<CofInventoryResponse> responses = new ArrayList<CofInventoryResponse>();

            if (id == null)
                responses.addAll(mapper.toResponses(cofInventoryService.findAll()));
            else{
                Optional<CofInventory> cofInventory = cofInventoryService.findById(id);
                if(cofInventory.isPresent()){
                    responses.add(mapper.toResponse(cofInventory.get()));
                }
            }
            if (responses.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(responses, HttpStatus.OK);
        } catch (Exception e) {
            log.error("getCofInventories|CofInventoryControllerAPI" + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<String> createCofInventory(
            @RequestBody @Valid CofInventoryRequest request
    ) {
        try{
            if(cofInventoryService.save(request)){
                return new ResponseEntity<>("CofInventory created", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Failed to create CofInventory", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            log.error("createCofInventory|CofInventoryControllerAPI: " + e.getMessage());
            return new ResponseEntity<>("Failed to create CofInventory, Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/replenish")
    public ResponseEntity<String> replenishCofInventory(
            @RequestBody @Valid CofInventoryReplenishRequest request,
            @RequestParam Long id) {
        try{
            Optional<CofInventory> cofInventory = cofInventoryService.findById(id);

            if(cofInventory.isPresent()) {

                cofInventoryService.replenish(id, request);
                return new ResponseEntity<>("CofInventory was updated successfully.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Cannot find CofInventory", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("replenishCofInventory|CofInventoryControllerAPI: " + e.getMessage());
            return new ResponseEntity<>("Failed to update CofInventory, Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<String> updateCofInventory(
            @RequestBody @Valid CofInventoryModelRequest request,
            @RequestParam Long id) {
        try{
            Optional<CofInventory> cofInventory = cofInventoryService.findById(id);

            if(cofInventory.isPresent()) {

                cofInventoryService.update(id, request);
                return new ResponseEntity<>("CofInventory was updated successfully.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Cannot find CofInventory", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("replenishCofInventory|CofInventoryControllerAPI: " + e.getMessage());
            return new ResponseEntity<>("Failed to update CofInventory, Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteCofInventory(
            @RequestParam Long id) {
        try {
            if (cofInventoryService.deleteById(id)) {
                return new ResponseEntity<>("CofInventory was deleted successfully.", HttpStatus.OK);
            }

            return new ResponseEntity<>("Cannot find CofInventory", HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            log.error("deleteCofInventory|CoffeeControllerAPI: " + e.getMessage());
            return new ResponseEntity<>("Cannot delete CofInventory.", HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
}
