package kg.devcats.coffee_shop.entity.postgres;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

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
