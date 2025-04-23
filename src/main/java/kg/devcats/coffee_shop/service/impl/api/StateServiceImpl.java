package kg.devcats.coffee_shop.service.impl.api;

import kg.devcats.coffee_shop.entity.postgres.State;
import kg.devcats.coffee_shop.payload.state.request.StateRequest;
import kg.devcats.coffee_shop.payload.state.request.StateRequestMVC;
import kg.devcats.coffee_shop.repository.postgres.StateRepositoryJPA;
import kg.devcats.coffee_shop.service.StateService;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class StateServiceImpl implements StateService {

    private final StateRepositoryJPA stateService;

    public StateServiceImpl(StateRepositoryJPA stateService) {
        this.stateService = stateService;
    }

    @Override
    public boolean save(StateRequest request) {
        State state = new State();
        state.setName(request.name());
        state.setPrefix(request.prefix());
        stateService.save(state);

        return true;
    }

    @Override
    public boolean save(StateRequestMVC request) {
        State state = new State();
        state.setName(request.getName());
        state.setPrefix(request.getPrefix());
        stateService.save(state);

        return true;
    }

    @Override
    public Optional<State> findById(String id) {
        return stateService.findById(id);
    }

    @Override
    public List<State> findAll() {
        return stateService.findAll();
    }

    @Override
    public boolean deleteById(String id) {
        if(!stateService.findById(id).isPresent()) {
            return false;
        }
        stateService.deleteById(id);
        return true;
    }
}
