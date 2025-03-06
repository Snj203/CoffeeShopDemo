package kg.devcats.coffee_shop.controller.API;

import jakarta.validation.Valid;
import kg.devcats.coffee_shop.entity.Warehouse;
import kg.devcats.coffee_shop.mapper.WarehouseMapper;
import kg.devcats.coffee_shop.payload.supplier.request.SupplierAddWarehouseRequest;
import kg.devcats.coffee_shop.payload.warehouse.request.WarehouseInitRequest;
import kg.devcats.coffee_shop.payload.warehouse.request.WarehouseRequest;
import kg.devcats.coffee_shop.payload.warehouse.response.WarehouseResponse;
import kg.devcats.coffee_shop.service.WarehouseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/warehouse")
public class WarehouseControllerAPI {
    private static final Logger log = LoggerFactory.getLogger(WarehouseControllerAPI.class);

    private final WarehouseService warehouseService;
    private final WarehouseMapper mapper;

    public WarehouseControllerAPI(WarehouseService warehouseService, WarehouseMapper mapper) {
        this.warehouseService = warehouseService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<WarehouseResponse>> getWarehouses(
            @RequestParam(required = false) Long id) {
        try {
            List<WarehouseResponse> responses = new ArrayList<WarehouseResponse>();

            if (id == null)
                responses.addAll(mapper.toResponses(warehouseService.findAll()));
            else{
                Optional<Warehouse> warehouse = warehouseService.findById(id);
                if(warehouse.isPresent()){
                    responses.add(mapper.toResponse(warehouse.get()));
                }
            }
            if (responses.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(responses, HttpStatus.OK);
        } catch (Exception e) {
            log.error("getWarehouses|WarehousesControllerAPI" + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<String> createWarehouse(
            @Valid @RequestBody WarehouseRequest request
    ) {
        try{
            if(warehouseService.save(request)){
                return new ResponseEntity<>("Warehouse created", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Failed to create Warehouse", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            log.error("createWarehouse|WarehousesControllerAPI: " + e.getMessage());
            return new ResponseEntity<>("Failed to create Warehouse, Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/init")
    public ResponseEntity<String> initWarehouse(
            @Valid @RequestBody WarehouseInitRequest request
    ) {
        try{
            if(warehouseService.createWithNoRelations(request)){
                return new ResponseEntity<>("Warehouse created", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Failed to create Warehouse", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            log.error("createWarehouse|WarehousesControllerAPI: " + e.getMessage());
            return new ResponseEntity<>("Failed to create Warehouse, Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<String> updateWarehouse(
            @Valid @RequestBody WarehouseRequest request,
            @RequestParam Long id) {
        try{
            Optional<Warehouse> warehouse = warehouseService.findById(id);

            if(warehouse.isPresent()) {

                warehouseService.update(id, request);
                return new ResponseEntity<>("Warehouse was updated successfully.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Cannot find Warehouse", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("updateWarehouse|WarehousesControllerAPI: " + e.getMessage());
            return new ResponseEntity<>("Failed to update Warehouse, Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping
    public ResponseEntity<String> deleteWarehouse(
            @RequestParam Long id) {
        try {
            if (warehouseService.deleteById(id)) {
                return new ResponseEntity<>("Warehouse was deleted successfully.", HttpStatus.OK);
            }

            return new ResponseEntity<>("Cannot find Warehouse", HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            log.error("deleteDeveloper|CoffeeControllerAPI: " + e.getMessage());
            return new ResponseEntity<>("Cannot delete Warehouse.", HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
}
