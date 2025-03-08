package kg.devcats.coffee_shop.payload.city.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CityRequestMVC {
    @NotNull
    @Size(min = 2, max = 32)
    @JsonProperty("name")
    private String name;

    @NotNull
    @Size(min = 1, max = 4)
    @JsonProperty("state")
    private String state;

    public CityRequestMVC() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
