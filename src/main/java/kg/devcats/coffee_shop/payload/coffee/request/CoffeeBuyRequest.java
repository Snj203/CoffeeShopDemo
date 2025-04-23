package kg.devcats.coffee_shop.payload.coffee.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CoffeeBuyRequest{
    @NotNull
    @NotBlank
    @Size(min = 2, max = 32)
    @JsonProperty("name")
    private String name;

    @NotNull
    @Min(1)
    @JsonProperty("amount")
    private Integer amount;

    @NotNull
    @JsonProperty("coffee_house_id")
    private Long coffeeHouseId;

    public CoffeeBuyRequest() {}

    public Long getCoffeeHouseId() {
        return coffeeHouseId;
    }

    public void setCoffeeHouseIId(Long coffeeHouseId) {
        this.coffeeHouseId = coffeeHouseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void setCoffeeHouseId(Long coffeeHouseId) {
        this.coffeeHouseId = coffeeHouseId;
    }
}
