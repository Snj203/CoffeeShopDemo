package kg.devcats.coffee_shop.mapper;

import kg.devcats.coffee_shop.entity.postgres.State;
import kg.devcats.coffee_shop.payload.state.response.StateResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StateMapper {
    public StateResponse toResponse(State state) {
         StateResponse response = new StateResponse(
             state.getName(),
             state.getPrefix()
         );

         return response;
    }

    public List<StateResponse> toResponses(List<State> stateList) {
        List<StateResponse> responses = new ArrayList<>();
        for (State state : stateList) {
            responses.add(toResponse(state));
        }
        return responses;
    }
}
