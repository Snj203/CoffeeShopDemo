package kg.devcats.coffee_shop.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "warehouse")
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wh_id")
    @JsonProperty("id")
    private Long id;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "wh_name")
    @JsonProperty("name")
    private String name;

    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonProperty("wh_coffee-list")
    private List<Coffee> coffeeList;

    @NotNull
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
        name = "warehouse_supplier",
        joinColumns = { @JoinColumn(name = "warehouse_id") },
        inverseJoinColumns = { @JoinColumn(name = "supplier_id") }
    )
    @JsonProperty("supplier-list")
    private List<Supplier> supplierList;

    @NotNull
    @Column(name = "wh_quantity")
    @JsonProperty("quantity")
    private int quantity;

    @NotNull
    @Column(name = "wh_time")
    @JsonProperty("last-change")
    private Timestamp time;

    public Warehouse() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Coffee> getCoffeeList() {
        return coffeeList;
    }

    public void setCoffeeList(List<Coffee> coffeeList) {
        this.coffeeList = coffeeList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addCoffee(Coffee coffee) {
        coffeeList.add(coffee);
    }
    public List<Supplier> getSupplierList() {
        return supplierList;
    }

    public void setSupplierList(List<Supplier> supplierList) {
        this.supplierList = supplierList;
    }

    public void addSupplier(Supplier supplier) {
        supplierList.add(supplier);
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

    @Override
    public String toString() {
        return "Warehouse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coffeeList=" + coffeeList +
                ", supplierList=" + supplierList +
                ", quantity=" + quantity +
                ", time=" + time +
                '}';
    }
}
