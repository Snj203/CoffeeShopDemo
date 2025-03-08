package kg.devcats.coffee_shop.payload.cof_inventory.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class CofInventoryReplenishRequest{
        @NotNull
        @Min(1)
        @JsonProperty("quantity")
        private Integer quantity;

        public CofInventoryReplenishRequest() {}

        public Integer getQuantity() {
                return quantity;
        }

        public void setQuantity(Integer quantity) {
                this.quantity = quantity;
        }
}
