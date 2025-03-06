package kg.devcats.coffee_shop.mapper;

import kg.devcats.coffee_shop.entity.CoffeeHouse;
import kg.devcats.coffee_shop.payload.coffeehouse.response.CoffeeHouseResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CoffeeHouseMapper {
    public CoffeeHouseResponse toResponse(CoffeeHouse coffee) {
        return null;
    }

    public List<CoffeeHouseResponse> toResponses(List<CoffeeHouse> coffeeList){
        List<CoffeeHouseResponse> responses = new ArrayList<>();
        for (CoffeeHouse coffee : coffeeList) {
            responses.add(toResponse(coffee));
        }
        return responses;
    }
}
