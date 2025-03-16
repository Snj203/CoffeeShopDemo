package kg.devcats.coffee_shop.payload.coffeehouse.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public class CoffeeHouseRequestMVC {
    @NotNull
    @JsonProperty("sold-coffee")
    private Integer soldCoffee;

    @NotNull
    @JsonProperty("city-name")
    private String city;

    @NotNull
    @JsonProperty("sold-merch")
    private Integer soldMerch;

    @NotNull
    @JsonProperty("total-sold")
    private Integer totalSold;

    public CoffeeHouseRequestMVC(){}

    public Integer getSoldCoffee() {
        return soldCoffee;
    }

    public void setSoldCoffee(Integer soldCoffee) {
        this.soldCoffee = soldCoffee;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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
}
