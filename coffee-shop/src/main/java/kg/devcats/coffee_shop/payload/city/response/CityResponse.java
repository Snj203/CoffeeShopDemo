package kg.devcats.coffee_shop.payload.city.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CityResponse(
        @JsonProperty("name")
        String name,

        @JsonProperty("state")
        String state
) {
}