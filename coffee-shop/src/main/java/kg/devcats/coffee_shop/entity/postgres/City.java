package kg.devcats.coffee_shop.entity.postgres;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "city")
public class City {
    @Id
    @Column(name = "city_name")
    @NotNull
    @Size(min = 2, max = 32)
    @JsonProperty("name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "city_state")
    @NotNull
    @JsonProperty("state")
    private State state;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Supplier> suppliers;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CoffeeHouse> coffeeHouses;

    public City() {
        this.suppliers = new ArrayList<>();
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "City{" +
                "name='" + name + '\'' +
                ", state=" + state +
                '}';
    }
}
