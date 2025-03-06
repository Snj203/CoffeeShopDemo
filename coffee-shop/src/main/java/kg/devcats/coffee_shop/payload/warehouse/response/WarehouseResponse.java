package kg.devcats.coffee_shop.payload.warehouse.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import kg.devcats.coffee_shop.entity.Coffee;
import kg.devcats.coffee_shop.entity.Supplier;

import java.sql.Timestamp;
import java.util.List;

public class WarehouseResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("coffees")
    private List<Coffee> coffeeList;

    @JsonProperty("suppliers")
    private List<Supplier> supplierList;

    @JsonProperty("quantity")
    private int quantity;

    @JsonProperty("last-change")
    private Timestamp time;

    public WarehouseResponse() {}

    public WarehouseResponse(Long id,String name, List<Coffee> coffeeList, List<Supplier> supplierList, int quantity, Timestamp time) {
        this.id = id;
        this.name = name;
        this.coffeeList = coffeeList;
        this.supplierList = supplierList;
        this.quantity = quantity;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Coffee> getCoffeeList() {
        return coffeeList;
    }

    public void setCoffeeList(List<Coffee> coffeeList) {
        this.coffeeList = coffeeList;
    }

    public List<Supplier> getSupplierList() {
        return supplierList;
    }

    public void setSupplierList(List<Supplier> supplierList) {
        this.supplierList = supplierList;
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
