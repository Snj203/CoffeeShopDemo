package kg.devcats.coffee_shop.payload.city.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CityRequest (
        @NotNull
        @NotBlank
        @Size(min = 2, max = 32)
        @JsonProperty("name")
        String name,

        @NotNull
        @NotEmpty
        @JsonProperty("state")
        String state
){ }
