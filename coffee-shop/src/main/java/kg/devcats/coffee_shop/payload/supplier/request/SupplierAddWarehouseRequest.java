package kg.devcats.coffee_shop.payload.supplier.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record SupplierAddWarehouseRequest (
        @NotNull
        @JsonProperty("warehouse-id")
        Long warehouseId,

        @NotNull
        @JsonProperty("supplier-id")
        Long supplierId
){
}
