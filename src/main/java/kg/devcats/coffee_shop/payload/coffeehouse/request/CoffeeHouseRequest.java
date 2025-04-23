package kg.devcats.coffee_shop.payload.coffeehouse.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CoffeeHouseRequest(
    @NotNull
    @Size(min = 1, max = 64)
    @JsonProperty("city")
    String city

) {
}
