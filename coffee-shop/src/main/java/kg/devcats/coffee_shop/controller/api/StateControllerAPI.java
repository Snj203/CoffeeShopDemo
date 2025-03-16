package kg.devcats.coffee_shop.controller.api;

import jakarta.validation.Valid;
import kg.devcats.coffee_shop.entity.postgres.State;
import kg.devcats.coffee_shop.mapper.StateMapper;
import kg.devcats.coffee_shop.payload.state.request.StateRequest;
import kg.devcats.coffee_shop.payload.state.response.StateResponse;
import kg.devcats.coffee_shop.service.StateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/state")
public class StateControllerAPI {

    private static final Logger log = LoggerFactory.getLogger(StateControllerAPI.class);

    private final StateService stateService;
    private final StateMapper stateMapper;

    public StateControllerAPI(StateService stateService, StateMapper stateMapper) {
        this.stateService = stateService;
        this.stateMapper = stateMapper;
    }

    @GetMapping
    public ResponseEntity<List<StateResponse>> getStates(
            @RequestParam(required = false) String name) {
        try {
            List<StateResponse> responses = new ArrayList<StateResponse>();

            if (name == null) {
                responses.addAll(stateMapper.toResponses(stateService.findAll()));
            } else{
                Optional<State> state = stateService.findById(name);
                if(state.isPresent()){
                    responses.add(stateMapper.toResponse(state.get()));
                }
            }
            if (responses.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(responses, HttpStatus.OK);
        } catch (Exception e) {
            log.error("getStates|StateControllerAPI: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<String> createState(
            @RequestBody @Valid StateRequest request
    ) {
        try{
            if(!stateService.save(request)){
                return new ResponseEntity<>("Failed to create State", HttpStatus.BAD_REQUEST);
            } else{
                return new ResponseEntity<>("State created", HttpStatus.CREATED);
            }
        } catch (Exception e) {
            log.error("createState|StateControllerAPI: " + e.getMessage());
            return new ResponseEntity<>("Failed to create State, Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteState(
            @RequestParam String name) {
        try {
            if(stateService.deleteById(name)){
                return new ResponseEntity<>("State was deleted successfully.", HttpStatus.OK);
            }
            return new ResponseEntity<>("Cannot find State", HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            log.error("deleteState|StateControllerAPI: " + e.getMessage());
            return new ResponseEntity<>("Cannot delete State.", HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
}
