package kg.devcats.coffee_shop.payload.cof_inventory.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CofInventoryRequest(

        @NotNull
        @JsonProperty("warehouse-id")
        Long warehouseId,

        @NotNull
        @Size(min = 2, max = 32)
        @JsonProperty("coffee-name")
        String coffeeName,

        @NotNull
        @JsonProperty("supplier-id")
        Long supplierId,

        @NotNull
        @JsonProperty("quantity")
        int quantity

){
}
