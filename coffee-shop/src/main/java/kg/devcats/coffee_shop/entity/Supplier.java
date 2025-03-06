package kg.devcats.coffee_shop.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "supplier")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonProperty("id")
    private Long id;

    @Column(name = "name")
    @NotNull
    @NotBlank
    @Size(min = 2, max = 32)
    @JsonProperty("name")
    private String name;

    @Column(name = "street")
    @NotNull
    @NotBlank
    @Size(min = 1, max = 64)
    @JsonProperty("street")
    private String street;

    @Column(name = "city")
    @NotNull
    @NotBlank
    @Size(min = 1, max = 64)
    @JsonProperty("city")
    private String city;

    @Column(name = "zip")
    @NotNull
    @JsonProperty("zip-code")
    private Integer zip;

    @Column(name = "state")
    @NotNull
    @NotBlank
    @Size(min = 1, max = 64)
    @JsonProperty("state")
    private String state;

    @NotNull
    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonProperty("coffee-list")
    private List<Coffee> coffeeList;

    @NotNull
    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonProperty("merch-list")
    private List<Merch> merchList;

    @NotNull
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "supplier_warehouse",
            joinColumns = { @JoinColumn(name = "supplier_id") },
            inverseJoinColumns = { @JoinColumn(name = "warehouse_id") }
    )
    private List<Warehouse> warehouseList;

    public Supplier() {}

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

    public List<Coffee> getCoffeeList() {
        return coffeeList;
    }

    public void setCoffeeList(List<Coffee> coffeeList) {
        this.coffeeList = coffeeList;
    }

    public void addCoffee(Coffee coffee) {
        this.coffeeList.add(coffee);
    }

    public List<Merch> getMerchList() {
        return merchList;
    }

    public void setMerchList(List<Merch> merchList) {
        this.merchList = merchList;
    }

    public void addMerch(Merch merch) {
        this.merchList.add(merch);
    }

    public List<Warehouse> getWarehouseList() {
        return warehouseList;
    }

    public void setWarehouseList(List<Warehouse> warehouseList) {
        this.warehouseList = warehouseList;
    }

    public void addWarehouse(Warehouse warehouse) {
        this.warehouseList.add(warehouse);
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", zip=" + zip +
                ", state='" + state + '\'' +
                ", coffeeList=" + coffeeList +
                ", merchList=" + merchList +
                ", warehouseList=" + warehouseList +
                '}';
    }
}