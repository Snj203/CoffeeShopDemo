package kg.devcats.coffee_shop.payload.coffeehouse.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CoffeeHouseResponse(
        @JsonProperty("id")
         Long id,

        @JsonProperty("city")
         String city,
        
        @JsonProperty("sold-coffee")
         Integer soldCoffee,
        
        @JsonProperty("sold-merch")
         Integer soldMerch,
        
        @JsonProperty("total-sold")
         Integer totalSold
) {

}
