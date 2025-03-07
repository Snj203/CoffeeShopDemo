package kg.devcats.coffee_shop.service;

import kg.devcats.coffee_shop.entity.State;
import kg.devcats.coffee_shop.payload.state.request.StateRequest;

import java.util.List;
import java.util.Optional;

public interface StateService {
    boolean save(StateRequest request);
    Optional<State> findById(String id);
    List<State> findAll();
    boolean deleteById(String id);
}
