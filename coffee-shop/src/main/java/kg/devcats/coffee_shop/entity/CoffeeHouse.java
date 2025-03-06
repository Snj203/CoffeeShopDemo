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
    @JsonProperty("id")
    private Long id;

    @Column(name = "city")
    @NotNull
    @NotBlank
    @Size(min = 1, max = 64)
    @JsonProperty("city")
    private String city;

    @Column(name = "sold_coffee")
    @NotNull
    @JsonProperty("sold-coffee")
    private Integer soldCoffee;


    @Column(name = "sold_merch")
    @NotNull
    @JsonProperty("sold-merch")
    private Integer soldMerch;

    @Column(name = "total_sold")
    @NotNull
    @JsonProperty("total-sold")
    private Integer totalSold;

    public CoffeeHouse() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getSoldCoffee() {
        return soldCoffee;
    }

    public void setSoldCoffee(Integer soldCoffee) {
        this.soldCoffee = soldCoffee;
    }

    public Integer getSoldMerch() {
        return soldMerch;
    }

    public void setSoldMerch(Integer soldMerch) {
        this.soldMerch = soldMerch;
    }

    public Integer getTotalSold() {
        return totalSold;
    }

    public void setTotalSold(Integer totalSold) {
        this.totalSold = totalSold;
    }

    @Override
    public String toString() {
        return "CoffeeHouse{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", soldCoffee=" + soldCoffee +
                ", soldMerch=" + soldMerch +
                ", totalSold=" + totalSold +
                '}';
    }
}
