package kg.devcats.coffee_shop.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "coffee_house")
public class CoffeeHouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "city")
    @NotNull
    @NotBlank
    @Size(min = 1, max = 64)
    @JsonProperty("city")
    private String city;

    @Column(name = "sold_coffee")
    @NotNull
    @JsonProperty("coffee")
    private Integer soldCoffee;


    @Column(name = "sold_merch")
    @NotNull
    @JsonProperty("merch")
    private Integer soldMerch;

    @Column(name = "total_sold")
    @NotNull
    @JsonProperty("total")
    private Integer totalSold;

    public CoffeeHouse() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull @NotBlank @Size(min = 1, max = 64) String getCity() {
        return city;
    }

    public void setCity(@NotNull @NotBlank @Size(min = 1, max = 64) String city) {
        this.city = city;
    }

    public @NotNull Integer getSoldCoffee() {
        return soldCoffee;
    }

    public void setSoldCoffee(@NotNull Integer soldCoffee) {
        this.soldCoffee = soldCoffee;
    }

    public @NotNull Integer getSoldMerch() {
        return soldMerch;
    }

    public void setSoldMerch(@NotNull Integer soldMerch) {
        this.soldMerch = soldMerch;
    }

    public @NotNull Integer getTotalSold() {
        return totalSold;
    }

    public void setTotalSold(@NotNull Integer totalSold) {
        this.totalSold = totalSold;
    }
}
