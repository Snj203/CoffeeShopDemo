package kg.devcats.coffee_shop.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
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
    @JsonProperty("city")
    private City city;

    @ManyToOne
    @JoinColumn(name = "sup_state")
    @NotNull
    @JsonProperty("state")
    private State state;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Coffee> coffees;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Merch> merches;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CofInventory> inventories;


    public Supplier() {
        coffees = new ArrayList<>();
        inventories = new ArrayList<>();
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


    @Override
    public String toString() {
        return "Supplier{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", street='" + street + '\'' +
                ", zip=" + zip +
                ", city=" + city +
                ", state=" + state +
                '}';
    }
}