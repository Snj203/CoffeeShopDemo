package kg.devcats.coffee_shop.mapper;

import kg.devcats.coffee_shop.entity.postgres.CoffeeHouse;
import kg.devcats.coffee_shop.payload.coffeehouse.response.CoffeeHouseResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CoffeeHouseMapper {
    public CoffeeHouseResponse toResponse(CoffeeHouse coffee) {
        CoffeeHouseResponse response = new CoffeeHouseResponse(
            coffee.getId(),
            coffee.getCity().getName(),
            coffee.getSoldCoffee(),
            coffee.getSoldMerch(),
            coffee.getTotalSold()
        );


        return response;
    }

    public List<CoffeeHouseResponse> toResponses(List<CoffeeHouse> coffeeList){
        List<CoffeeHouseResponse> responses = new ArrayList<>();
        for (CoffeeHouse coffee : coffeeList) {
            responses.add(toResponse(coffee));
        }
        return responses;
    }
}
