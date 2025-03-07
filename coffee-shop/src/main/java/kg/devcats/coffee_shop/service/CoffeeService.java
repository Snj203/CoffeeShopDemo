package kg.devcats.coffee_shop.service;

import kg.devcats.coffee_shop.entity.Coffee;
import kg.devcats.coffee_shop.payload.coffee.request.CoffeeRequest;

import java.util.List;
import java.util.Optional;

public interface CoffeeService {
    boolean save(CoffeeRequest request);
    Optional<Coffee> findById(String id);
    List<Coffee> findAll();
    boolean deleteByIdCoffee(String id);
    boolean update(String id, CoffeeRequest request);
}
