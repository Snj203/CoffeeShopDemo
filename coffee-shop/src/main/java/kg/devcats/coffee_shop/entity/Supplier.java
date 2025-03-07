package kg.devcats.coffee_shop.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "supplier")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sup_id")
    @JsonProperty("id")
    private Long id;

    @Column(name = "sup_name")
    @NotNull
    @NotBlank
    @Size(min = 2, max = 32)
    @JsonProperty("name")
    private String name;

    @Column(name = "sup_street")
    @NotNull
    @NotBlank
    @Size(min = 1, max = 64)
    @JsonProperty("street")
    private String street;

    @Column(name = "sup_zip")
    @NotNull
    @JsonProperty("zip-code")
    private Integer zip;

    @ManyToOne
    @JoinColumn(name = "sup_city")
    @NotNull
    @NotEmpty
    @JsonProperty("city")
    private City city;

    @ManyToOne
    @JoinColumn(name = "sup_state")
    @NotNull
    @NotEmpty
    @JsonProperty("state")
    private State state;

    @NotNull
    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonProperty("coffee-list")
    private List<Coffee> coffeeList;

    @NotNull
    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonProperty("merch-list")
    private List<Merch> merchList;

    @NotNull
    @ManyToMany(mappedBy = "supplierList")
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

    public Integer getZip() {
        return zip;
    }

    public void setZip(Integer zip) {
        this.zip = zip;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
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

    @Override
    public String toString() {
        return "Supplier{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", street='" + street + '\'' +
                ", zip=" + zip +
                ", city=" + city +
                ", state=" + state +
                ", coffeeList=" + coffeeList +
                ", merchList=" + merchList +
                ", warehouseList=" + warehouseList +
                '}';
    }
}