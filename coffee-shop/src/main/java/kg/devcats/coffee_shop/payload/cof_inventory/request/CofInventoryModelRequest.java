package kg.devcats.coffee_shop.payload.cof_inventory.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CofInventoryModelRequest {
    @NotNull
    @JsonProperty("warehouse-id")
    private Long warehouseId;

    @NotNull
    @Size(min = 2, max = 32)
    @JsonProperty("coffee-name")
    private String coffeeName;

    @NotNull
    @JsonProperty("supplier-id")
    private Long supplierId;

    @NotNull
    @JsonProperty("quantity")
    private Integer quantity;

    public CofInventoryModelRequest() {
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getCoffeeName() {
        return coffeeName;
    }

    public void setCoffeeName(String coffeeName) {
        this.coffeeName = coffeeName;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}
