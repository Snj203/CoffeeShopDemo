package kg.devcats.coffee_shop.payload.merch.response;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;

public record MerchResponse(
        @JsonProperty("id")
        Long itemId,

        @JsonProperty("name")
        String name,
        
        @JsonProperty("amount")
        Integer quantity,
        
        @JsonProperty("last-change")
        Timestamp time,
        
        @JsonProperty("supplier")
        Long supplier
) {

}
