package kg.devcats.coffee_shop.payload.cof_inventory.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;

public record CofInventoryResponse (
        @JsonProperty("id")
        Long id,

        @JsonProperty("warehouse-id")
        Long warehouseId,
        
        @JsonProperty("coffee-name")
        String coffeeName,
        
        @JsonProperty("supplier-id")
        Long supplierId,
        
        @JsonProperty("quantity")
        int quantity,
        
        @JsonProperty("last-change")
        Timestamp time
){
}
