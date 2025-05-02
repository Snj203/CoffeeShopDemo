package kg.devcats.coffee_shop.payload.coffee.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kg.devcats.coffee_shop.annotations.validation.MultipartFileSizeValid;
import org.springframework.web.multipart.MultipartFile;


public class CoffeeRequestMVC {
    @NotNull
    @NotBlank
    @Size(min = 2, max = 32)
    @JsonProperty("name")
    private String name;

    @NotNull
    @JsonProperty("price")
    private double price;

    @NotNull
    @JsonProperty("sold")
    private int sold;

    @NotNull
    @JsonProperty("total-sold")
    private int total;

    @MultipartFileSizeValid
    @JsonProperty("photo")
    private MultipartFile photo;

    @JsonProperty("supplier")
    private Long supplierId;

    public CoffeeRequestMVC() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public MultipartFile getPhoto() {
        return photo;
    }

    public void setPhoto(MultipartFile photo) {
        this.photo = photo;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }
}
