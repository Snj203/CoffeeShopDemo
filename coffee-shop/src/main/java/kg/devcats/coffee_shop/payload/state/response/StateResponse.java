package kg.devcats.coffee_shop.payload.state.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record StateResponse(
        @JsonProperty("name")
        String name,

        @JsonProperty("prefix")
        Long prefix
) {

}
