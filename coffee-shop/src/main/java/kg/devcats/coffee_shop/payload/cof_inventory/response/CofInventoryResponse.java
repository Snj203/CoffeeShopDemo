package kg.devcats.coffee_shop.payload.cof_inventory.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;

public class CofInventoryResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("warehouse-id")
    private Long warehouseId;

    @JsonProperty("coffee-name")
    private String coffeeName;

    @JsonProperty("supplier-id")
    private Long supplierId;

    @JsonProperty("quantity")
    private int quantity;

    @JsonProperty("last-change")
    private Timestamp time;

    public CofInventoryResponse() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
