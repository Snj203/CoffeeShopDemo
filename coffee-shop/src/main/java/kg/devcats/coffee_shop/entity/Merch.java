package kg.devcats.coffee_shop.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "merch_inventory")
public class Merch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mer_item_id")
    @JsonProperty("id")
    private Long itemId;

    @Column(name = "mer_item_name")
    @NotNull
    @NotBlank
    @Size(min = 2, max = 64)
    @JsonProperty("name")
    private String name;

    @Column(name = "mer_quantity")
    @NotNull
    @JsonProperty("amount")
    private Integer quantity;

    @NotNull
    @Column(name = "mer_time")
    @JsonProperty("last-change")
    private Timestamp time;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mer_sup_id", nullable = false)
    @JsonProperty("supplier")
    private Supplier supplier;

    public Merch() {}

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    @Override
    public String toString() {
        return "Merch{" +
                "itemId=" + itemId +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", time=" + time +
                ", supplier=" + supplier +
                '}';
    }
}