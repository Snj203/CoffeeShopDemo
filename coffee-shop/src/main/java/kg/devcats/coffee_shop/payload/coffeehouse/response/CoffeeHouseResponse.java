package kg.devcats.coffee_shop.payload.coffeehouse.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoffeeHouseResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("city")
    private String city;

    @JsonProperty("sold-coffee")
    private Integer soldCoffee;

    @JsonProperty("sold-merch")
    private Integer soldMerch;

    @JsonProperty("total-sold")
    private Integer totalSold;

    public CoffeeHouseResponse() {}

    public CoffeeHouseResponse(Long id, String city, Integer soldCoffee, Integer soldMerch, Integer totalSold) {
        this.id = id;
        this.city = city;
        this.soldCoffee = soldCoffee;
        this.soldMerch = soldMerch;
        this.totalSold = totalSold;
    }

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
}
