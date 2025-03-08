package kg.devcats.coffee_shop.payload.supplier.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class SupplierRequestMVC {
    @NotNull
    @NotBlank
    @Size(min = 2, max = 32)
    @JsonProperty("name")
    private String name;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 64)
    @JsonProperty("street")
    private String street;

    @NotNull
    @JsonProperty("zip-code")
    private Integer zip;

    @NotNull
    @JsonProperty("city")
    private String cityName;

    @NotNull
    @JsonProperty("state")
    private String stateName;

    public SupplierRequestMVC() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getZip() {
        return zip;
    }

    public void setZip(Integer zip) {
        this.zip = zip;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }
}
