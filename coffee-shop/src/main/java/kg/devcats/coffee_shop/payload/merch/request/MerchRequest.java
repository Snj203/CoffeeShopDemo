package kg.devcats.coffee_shop.payload.merch.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MerchRequest(
        @NotNull
        @NotBlank
        @Size(min = 2, max = 64)
        @JsonProperty("item-name")
        String itemName,

        @NotNull
        @JsonProperty("supplier")
        Long supplierId
) {
}
