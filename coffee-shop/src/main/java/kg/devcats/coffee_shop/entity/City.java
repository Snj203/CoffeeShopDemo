package kg.devcats.coffee_shop.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "city")
public class City {
    @Id
    @Column(name = "city_name")
    @NotNull
    @NotBlank
    @Size(min = 2, max = 32)
    @JsonProperty("name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "city_state")
    @NotNull
    @NotEmpty
    @JsonProperty("state")
    private State state;

    public City() {}

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
