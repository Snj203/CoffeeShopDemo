package kg.devcats.coffee_shop.service.api;

import kg.devcats.coffee_shop.entity.Coffee;
import kg.devcats.coffee_shop.payload.coffee.request.CoffeeRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface CoffeeService {
    boolean save(CoffeeRequest request, MultipartFile photo);
    Optional<Coffee> findById(String id);
    List<Coffee> findAll();
    boolean deleteByIdCoffee(String id);
    boolean update(CoffeeRequest request, MultipartFile photo);
}
