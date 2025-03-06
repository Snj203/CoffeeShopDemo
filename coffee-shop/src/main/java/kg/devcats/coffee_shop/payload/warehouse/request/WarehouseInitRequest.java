package kg.devcats.coffee_shop.payload.warehouse.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record WarehouseInitRequest(
        @NotNull
        @Size(min = 1, max = 50)
        String name
) {
}
