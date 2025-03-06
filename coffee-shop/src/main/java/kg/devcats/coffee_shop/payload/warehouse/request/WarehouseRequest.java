package kg.devcats.coffee_shop.payload.warehouse.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record WarehouseRequest (

        @NotNull
        @Size(min = 1, max = 50)
        String name,

        @NotNull
        @NotEmpty
        List<Long>coffeeList,

        @NotNull
        @NotEmpty
        List<Long>supplierList
){
}
