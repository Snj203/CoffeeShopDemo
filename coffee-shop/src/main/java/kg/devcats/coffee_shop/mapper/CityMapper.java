package kg.devcats.coffee_shop.mapper;

import kg.devcats.coffee_shop.entity.postgres.City;
import kg.devcats.coffee_shop.payload.city.response.CityResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CityMapper {
    public CityResponse toResponse(City city) {
        CityResponse response = new CityResponse(
            city.getName(),
            city.getState().getName()
        );

        return response;
    }

    public List<CityResponse> toResponses(List<City> cityList) {
        List<CityResponse> responses = new ArrayList<>();
        for (City city : cityList) {
            responses.add(toResponse(city));
        }
        return responses;
    }
}
