package kg.devcats.coffee_shop.payload.coffee.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record CoffeeUpdateRequest (
    @NotNull
    @JsonProperty("price")
    double price,

    @NotNull
    @JsonProperty("supplier")
    Long supplierId){
}
