package kg.devcats.coffee_shop.payload.merch.response;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;

public class MerchResponse {

    @JsonProperty("id")
    private Long itemId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("amount")
    private Integer quantity;

    @JsonProperty("last-change")
    private Timestamp time;

    @JsonProperty("supplier")
    private Long supplier;

    public MerchResponse() {}

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

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Long getSupplier() {
        return supplier;
    }

    public void setSupplier(Long supplier) {
        this.supplier = supplier;
    }
}
