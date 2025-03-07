package kg.devcats.coffee_shop.payload.city.response;

import com.fasterxml.jackson.annotation.JsonProperty;
public class CityResponse {
    @JsonProperty("name")
    private String name;

    @JsonProperty("state")
    private String state;

    public CityResponse() {
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
