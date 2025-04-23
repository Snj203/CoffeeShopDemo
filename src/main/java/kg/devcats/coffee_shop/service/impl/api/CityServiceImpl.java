package kg.devcats.coffee_shop.service.impl.api;

import kg.devcats.coffee_shop.entity.postgres.City;
import kg.devcats.coffee_shop.entity.postgres.State;
import kg.devcats.coffee_shop.payload.city.request.CityRequest;
import kg.devcats.coffee_shop.payload.city.request.CityRequestMVC;
import kg.devcats.coffee_shop.repository.postgres.CityRepositoryJPA;
import kg.devcats.coffee_shop.repository.postgres.StateRepositoryJPA;
import kg.devcats.coffee_shop.service.CityService;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CityServiceImpl implements CityService {

    private final CityRepositoryJPA cityService;
    private final StateRepositoryJPA stateService;

    public CityServiceImpl(CityRepositoryJPA cityService, StateRepositoryJPA stateService) {
        this.cityService = cityService;
        this.stateService = stateService;
    }


    @Override
    public boolean save(CityRequest request) {
        Optional<State> optionalState = stateService.findById(request.state());
        if(!optionalState.isPresent()){
            return false;
        }
        City city = new City();
        city.setName(request.name());
        city.setState(optionalState.get());
        cityService.save(city);
        return true;
    }

    @Override
    public boolean save(CityRequestMVC request) {
        Optional<State> state = stateService.findById(request.getState());
        if(!state.isPresent()){
            return false;
        }
        City city = new City();
        city.setName(request.getName());
        city.setState(state.get());
        cityService.save(city);
        return true;
    }

    @Override
    public Optional<City> findById(String id) {
        return cityService.findById(id);
    }

    @Override
    public List<City> findAll() {
        return cityService.findAll();
    }

    @Override
    public boolean deleteById(String id) {
        if(!cityService.findById(id).isPresent()) {
            return false;
        }
        cityService.deleteById(id);
        return true;
    }
}
