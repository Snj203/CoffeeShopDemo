package kg.devcats.coffee_shop.payload.cof_inventory.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record CofInventoryReplenishRequest (
        @NotNull
        @JsonProperty("quantity")
        int quantity

){
}
