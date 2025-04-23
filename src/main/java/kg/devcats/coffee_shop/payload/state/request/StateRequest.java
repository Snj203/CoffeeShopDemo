package kg.devcats.coffee_shop.payload.state.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record StateRequest(
        @NotNull
        @NotBlank
        @Size(min = 0, max = 4)
        @JsonProperty("name")
        String name,

        @NotNull
        @JsonProperty("prefix")
        Long prefix
) {
}
