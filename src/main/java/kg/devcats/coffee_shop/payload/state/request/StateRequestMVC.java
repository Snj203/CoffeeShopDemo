package kg.devcats.coffee_shop.payload.state.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

public class StateRequestMVC {
    @NotNull
    @NotBlank
    @Size(min = 1, max = 4)
    @JsonProperty("name")
    private String name;

    @NotNull
    @Min(1000)
    @Max(9999)
    @JsonProperty("prefix")
    Long prefix;

    public StateRequestMVC() {}

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
