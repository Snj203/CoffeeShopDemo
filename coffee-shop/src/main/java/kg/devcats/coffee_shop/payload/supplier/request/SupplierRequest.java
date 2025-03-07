package kg.devcats.coffee_shop.payload.supplier.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record SupplierRequest(
        @NotNull
        @NotBlank
        @Size(min = 2, max = 32)
        @JsonProperty("name")
        String name,

        @NotNull
        @NotBlank
        @Size(min = 1, max = 64)
        @JsonProperty("street")
        String street,

        @NotNull
        @NotBlank
        @Size(min = 1, max = 64)
        @JsonProperty("city")
        String city,

        @NotNull
        @JsonProperty("zip-code")
        Integer zip,

        @NotNull
        @NotBlank
        @Size(min = 1, max = 64)
        @JsonProperty("state")
        String state


) {
}
