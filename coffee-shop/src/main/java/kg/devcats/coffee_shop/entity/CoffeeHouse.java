package kg.devcats.coffee_shop.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(name = "coffee_house")
public class CoffeeHouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ch_id")
    @JsonProperty("id")
    private Long id;

    @Column(name = "ch_sold_coffee")
    @NotNull
    @JsonProperty("sold-coffee")
    private Integer soldCoffee;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ch_city")
    @NotNull
    @JsonProperty("city")
    private City city;

    @Column(name = "ch_sold_merch")
    @NotNull
    @JsonProperty("sold-merch")
    private Integer soldMerch;

    @Column(name = "ch_total_sold")
    @NotNull
    @JsonProperty("total-sold")
    private Integer totalSold;

    @Column(name = "ch_state_prefix")
    @NotNull
    @JsonProperty("state-prefix")
    private Long prefixState;

    public CoffeeHouse() {}

    public Long getPrefixState() {
        return prefixState;
    }

    public void setPrefixState(Long prefixState) {
        this.prefixState = prefixState;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
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
