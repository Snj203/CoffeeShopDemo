package kg.devcats.coffee_shop.controller.api;

import jakarta.validation.Valid;
import kg.devcats.coffee_shop.entity.postgres.Supplier;
import kg.devcats.coffee_shop.mapper.SupplierMapper;
import kg.devcats.coffee_shop.payload.supplier.request.SupplierRequest;
import kg.devcats.coffee_shop.payload.supplier.response.SupplierResponse;
import kg.devcats.coffee_shop.service.api.SupplierService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/supplier")
public class SupplierControllerAPI {
    private static final Logger log = LoggerFactory.getLogger(SupplierControllerAPI.class);

    private final SupplierService supplierService;
    private final SupplierMapper mapper;

    public SupplierControllerAPI(SupplierService supplierService, SupplierMapper mapper) {
        this.supplierService = supplierService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<SupplierResponse>> getSuppliers(
            @RequestParam(required = false) Long id) {
        try {
            List<SupplierResponse> responses = new ArrayList<SupplierResponse>();

            if (id == null)
                responses.addAll(mapper.toResponses(supplierService.findAll()));
            else{
                Optional<Supplier> supplier = supplierService.findById(id);
                if(supplier.isPresent()){
                    responses.add(mapper.toResponse(supplier.get()));
                }
            }
            if (responses.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(responses, HttpStatus.OK);
        } catch (Exception e) {
            log.error("getSuppliers|SuppliersControllerAPI" + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<String> createSupplier(
            @RequestBody @Valid SupplierRequest request
    ) {
        try{
            if(supplierService.save(request)){
                return new ResponseEntity<>("supplier created", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Failed to create supplier", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            log.error("createSupplier|SuppliersControllerAPI: " + e.getMessage());
            return new ResponseEntity<>("Failed to create supplier, Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<String> updateSupplier(
            @RequestBody @Valid SupplierRequest request,
            @RequestParam Long id) {
        try{
            Optional<Supplier> supplier = supplierService.findById(id);

            if(supplier.isPresent()) {

                supplierService.update(id, request);
                return new ResponseEntity<>("supplier was updated successfully.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Cannot find supplier", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("updateSupplier|SuppliersControllerAPI: " + e.getMessage());
            return new ResponseEntity<>("Failed to update supplier, Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping
    public ResponseEntity<String> deleteSupplier(
            @RequestParam Long id) {
        try {
            if (supplierService.deleteById(id)) {
                return new ResponseEntity<>("supplier was deleted successfully.", HttpStatus.OK);
            }

            return new ResponseEntity<>("Cannot find supplier", HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            log.error("deleteDeveloper|CoffeeControllerAPI: " + e.getMessage());
            return new ResponseEntity<>("Cannot delete supplier.", HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
}
