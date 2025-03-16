package kg.devcats.coffee_shop.payload.merch.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class MerchRequestMVC {
    @JsonProperty("id")
    private Long itemId;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 64)
    @JsonProperty("name")
    private String name;

    @NotNull
    @JsonProperty("quantity")
    private Integer quantity;

    @NotNull
    @JsonProperty("supplierId")
    private Long supplierId;

    public MerchRequestMVC() {}

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }
}
