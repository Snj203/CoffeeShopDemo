package kg.devcats.coffee_shop.payload.coffee.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoffeeResponse{

    @JsonProperty("name")
    private String name;

    @JsonProperty("price")
    private double price;

    @JsonProperty("sold")
    private int sold;

    @JsonProperty("total-sold")
    private int total;

    @JsonProperty("supplier")
    private Long supplierId;

    public CoffeeResponse(){}

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

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }
}
