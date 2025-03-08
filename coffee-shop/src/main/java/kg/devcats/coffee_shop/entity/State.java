package kg.devcats.coffee_shop.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "state", uniqueConstraints = {@UniqueConstraint(columnNames = {"st_prefix"})})
public class State {
    @Id
    @Column(name = "st_name")
    @NotNull
    @NotBlank
    @Size(min = 1, max = 4)
    @JsonProperty("name")
    private String name;

    @Column(name = "st_prefix", unique = true)
    @NotNull
    @Min(1000)
    @Max(9999)
    @JsonProperty("prefix")
    Long prefix;

    @OneToMany(mappedBy = "state", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<City> cities;

    @OneToMany(mappedBy = "state", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Supplier> suppliers;

    public State() {
        this.cities = new ArrayList<>();
        this.suppliers = new ArrayList<>();
    }

    public Long getPrefix() {
        return prefix;
    }

    public void setPrefix(Long prefix) {
        this.prefix = prefix;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "State{" +
                "name='" + name + '\'' +
                ", prefix=" + prefix +
                '}';
    }
}
