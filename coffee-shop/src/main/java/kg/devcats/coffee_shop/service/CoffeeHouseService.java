package kg.devcats.coffee_shop.service;

import kg.devcats.coffee_shop.entity.CoffeeHouse;
import kg.devcats.coffee_shop.payload.coffeehouse.request.CoffeeHouseRequest;

import java.util.List;
import java.util.Optional;

public interface CoffeeHouseService {
    boolean save(CoffeeHouseRequest request);
    Optional<CoffeeHouse> findById(Long id);
    List<CoffeeHouse> findAll();
    boolean deleteByIdCoffee(Long id);
    boolean update(Long id, CoffeeHouseRequest request);
}
