package kg.devcats.coffee_shop.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;
@Entity
@Table(name = "cof_inventory")
public class CofInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ci_id")
    @JsonProperty("id")
    private Long id;

    @NotNull
    @Column(name = "ci_warehouse_id")
    @JsonProperty("warehouse-id")
    private Long warehouseId;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "ci_coffee_name")
    @JsonProperty("coffee-name")
    private Coffee coffee;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "ci_sup_id")
    @JsonProperty("supplier-id")
    private Supplier supplier;

    @NotNull
    @Column(name = "ci_quantity")
    @JsonProperty("quantity")
    private int quantity;

    @NotNull
    @Column(name = "ci_time")
    @JsonProperty("last-change")
    private Timestamp time;

    public CofInventory() {
    }

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

    public Coffee getCoffee() {
        return coffee;
    }

    public void setCoffee(Coffee coffee) {
        this.coffee = coffee;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
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
