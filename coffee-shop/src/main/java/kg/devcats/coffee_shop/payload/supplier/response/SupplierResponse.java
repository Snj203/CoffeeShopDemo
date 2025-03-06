package kg.devcats.coffee_shop.payload.supplier.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import kg.devcats.coffee_shop.entity.Coffee;
import kg.devcats.coffee_shop.entity.Merch;
import kg.devcats.coffee_shop.entity.Warehouse;

import java.util.List;

public class SupplierResponse {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("street")
    private String street;

    @JsonProperty("city")
    private String city;

    @JsonProperty("zip-code")
    private Integer zip;

    @JsonProperty("state")
    private String state;

    @JsonProperty("coffees")
    private List<Coffee> coffeeList;

    @JsonProperty("merches")
    private List<Merch> merchList;

    @JsonProperty("warehouses")
    private List<Warehouse> warehouseList;

    public SupplierResponse() {
    }

    public SupplierResponse(Long id, String name, String street, String city, Integer zip, String state, List<Coffee> coffeeList, List<Merch> merchList, List<Warehouse> warehouseList) {
        this.id = id;
        this.name = name;
        this.street = street;
        this.city = city;
        this.zip = zip;
        this.state = state;
        this.coffeeList = coffeeList;
        this.merchList = merchList;
        this.warehouseList = warehouseList;
    }

    public List<Coffee> getCoffeeList() {
        return coffeeList;
    }

    public void setCoffeeList(List<Coffee> coffeeList) {
        this.coffeeList = coffeeList;
    }

    public List<Merch> getMerchList() {
        return merchList;
    }

    public void setMerchList(List<Merch> merchList) {
        this.merchList = merchList;
    }

    public List<Warehouse> getWarehouseList() {
        return warehouseList;
    }

    public void setWarehouseList(List<Warehouse> warehouseList) {
        this.warehouseList = warehouseList;
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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getZip() {
        return zip;
    }

    public void setZip(Integer zip) {
        this.zip = zip;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
