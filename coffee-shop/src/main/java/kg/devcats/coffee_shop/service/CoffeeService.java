package kg.devcats.coffee_shop.service;

import kg.devcats.coffee_shop.entity.Coffee;
import kg.devcats.coffee_shop.payload.coffee.request.CoffeeRequest;
import kg.devcats.coffee_shop.payload.coffee.request.CoffeeUpdateRequest;

import java.util.List;
import java.util.Optional;

public interface CoffeeService {
    boolean save(CoffeeRequest request);
    Optional<Coffee> findById(Long id);
    List<Coffee> findAll();
    boolean deleteByIdCoffee(Long id);
    boolean update(Long id, CoffeeUpdateRequest request);
}
