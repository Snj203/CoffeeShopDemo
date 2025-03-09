package kg.devcats.coffee_shop.mapper;

import kg.devcats.coffee_shop.entity.postgres.Coffee;
import kg.devcats.coffee_shop.payload.coffee.response.CoffeeResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CoffeeMapper {

    public CoffeeResponse toResponse(Coffee coffee) {
        CoffeeResponse coffeeResponse = new CoffeeResponse();
        coffeeResponse.setName(coffee.getName());
        coffeeResponse.setPrice(coffee.getPrice());
        coffeeResponse.setSupplierId(coffee.getSupplier().getId());
        coffeeResponse.setSold(coffee.getSold());
        coffeeResponse.setTotal(coffee.getTotal());
        coffeeResponse.setPhoto(coffee.getPhoto());

        return coffeeResponse;
    }

    public List<CoffeeResponse> toResponses(List<Coffee> coffeeList){
        List<CoffeeResponse> responses = new ArrayList<>();
        for (Coffee coffee : coffeeList) {
            responses.add(toResponse(coffee));
        }
        return responses;
    }
}
