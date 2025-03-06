package kg.devcats.coffee_shop.payload.coffee.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CoffeeUpdateRequest(
        @NotNull
        @NotBlank
        @Size(min = 2, max = 32)
        @JsonProperty("name")
        String name,

        @NotNull
        double price

){
}
