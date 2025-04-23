package kg.devcats.coffee_shop.payload.supplier.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SupplierResponse(
        @JsonProperty("id")
         Long id,

        @JsonProperty("name")
         String name,
        
        @JsonProperty("street")
         String street,
        
        @JsonProperty("city")
         String city,
        
        @JsonProperty("zip-code")
         Integer zip,
        
        @JsonProperty("state")
         String state
) {

}
