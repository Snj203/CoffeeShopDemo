package kg.devcats.coffee_shop.payload.coffee.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CoffeeResponse(
        @JsonProperty("name")
         String name,

        @JsonProperty("price")
         double price,
        
        @JsonProperty("sold")
         int sold,
        
        @JsonProperty("total-sold")
         int total,
        
        @JsonProperty("supplier")
         Long supplierId,
        
        @JsonProperty("photo")
         String photo
){
}
