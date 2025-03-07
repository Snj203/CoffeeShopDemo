package kg.devcats.coffee_shop.payload.state.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StateResponse {
    @JsonProperty("name")
    private String name;

    @JsonProperty("prefix")
    Long prefix;

    public StateResponse() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrefix() {
        return prefix;
    }

    public void setPrefix(Long prefix) {
        this.prefix = prefix;
    }
}
