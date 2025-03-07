package kg.devcats.coffee_shop.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "state", uniqueConstraints = {@UniqueConstraint(columnNames = {"st_prefix"})})
public class State {
    @Id
    @Column(name = "st_name")
    @NotNull
    @NotBlank
    @Size(min = 0, max = 4)
    @JsonProperty("name")
    private String name;

    @Column(name = "st_prefix", unique = true)
    @NotNull
    @JsonProperty("prefix")
    Long prefix;

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
