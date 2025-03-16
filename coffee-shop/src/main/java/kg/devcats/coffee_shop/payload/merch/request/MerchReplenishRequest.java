package kg.devcats.coffee_shop.payload.merch.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class MerchReplenishRequest {
    @NotNull
    @Min(1)
    private Integer quantity;

    public MerchReplenishRequest() {}

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
