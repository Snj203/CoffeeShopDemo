package kg.devcats.coffee_shop.service;

import kg.devcats.coffee_shop.entity.City;
import kg.devcats.coffee_shop.payload.city.request.CityRequest;
import kg.devcats.coffee_shop.payload.city.request.CityRequestMVC;

import java.util.List;
import java.util.Optional;

public interface CityService {
    boolean save(CityRequest request);
    boolean save(CityRequestMVC request);
    Optional<City> findById(String id);
    List<City> findAll();
    boolean deleteById(String id);
}
