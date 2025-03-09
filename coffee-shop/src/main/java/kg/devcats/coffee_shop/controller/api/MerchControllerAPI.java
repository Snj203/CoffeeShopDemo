package kg.devcats.coffee_shop.controller.api;

import jakarta.validation.Valid;
import kg.devcats.coffee_shop.entity.Merch;
import kg.devcats.coffee_shop.mapper.MerchMapper;
import kg.devcats.coffee_shop.payload.merch.request.MerchRequest;
import kg.devcats.coffee_shop.payload.merch.request.MerchUpdateRequest;
import kg.devcats.coffee_shop.payload.merch.response.MerchResponse;
import kg.devcats.coffee_shop.service.api.MerchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/merch")
public class MerchControllerAPI {
    private static final Logger log = LoggerFactory.getLogger(CoffeeControllerAPI.class);

    private final MerchService merchService;
    private final MerchMapper mapper;

    public MerchControllerAPI(MerchService merchService, MerchMapper mapper) {
        this.merchService = merchService;
        this.mapper = mapper;
    }


    @GetMapping
    public ResponseEntity<List<MerchResponse>> getMerch(
            @RequestParam(required = false) Long id) {
        try {
            List<MerchResponse> responses = new ArrayList<MerchResponse>();

            if (id == null)
                responses.addAll(mapper.toResponses(merchService.findAll()));
            else{
                Optional<Merch> merch = merchService.findById(id);
                if(merch.isPresent()){
                    responses.add(mapper.toResponse(merch.get()));
                }
            }
            if (responses.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(responses, HttpStatus.OK);
        } catch (Exception e) {
            log.error("getMerch|MerchControllerAPI: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<String> createMerch(
            @RequestBody @Valid MerchRequest request
    ) {
        try{
            if(merchService.save(request)){
                return new ResponseEntity<>("Merch created", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Failed to create Merch", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            log.error("createMerch|MerchControllerAPI: " + e.getMessage());
            return new ResponseEntity<>("Failed to create Merch, Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<String> updateMerch(
            @RequestBody @Valid MerchUpdateRequest request,
            @RequestParam Long id) {
        try{
            Optional<Merch> merch = merchService.findById(id);

            if(merch.isPresent()) {

                merchService.update(id, request);
                return new ResponseEntity<>("Merch was updated successfully.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Cannot find Merch", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("updateMerch|MerchControllerAPI: " + e.getMessage());
            return new ResponseEntity<>("Cannot update Merch.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping
    public ResponseEntity<String> deleteMerch(
            @RequestParam Long id) {
        try {
            if (merchService.deleteById(id)) {
                return new ResponseEntity<>("Merch was deleted successfully.", HttpStatus.OK);
            }

            return new ResponseEntity<>("Cannot find Merch", HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            log.error("deleteMerch|MerchControllerAPI: " + e.getMessage());
            return new ResponseEntity<>("Cannot delete Merch.", HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
}
