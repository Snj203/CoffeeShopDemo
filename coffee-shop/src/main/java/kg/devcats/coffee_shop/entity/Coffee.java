package kg.devcats.coffee_shop.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "coffee")
public class Coffee {

    @Id
    @Column(name = "cof_name")
    @NotNull
    @NotBlank
    @Size(min = 2, max = 32)
    @JsonProperty("name")
    private String name;

    @Column(name = "cof_price")
    @NotNull
    @JsonProperty("price")
    private double price;

    @Column(name = "cof_sold")
    @NotNull
    @JsonProperty("sold")
    private int sold;

    @Column(name = "cof_total_sold")
    @NotNull
    @JsonProperty("total-sold")
    private int total;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "cof_sup_id", nullable = false)
    @JsonProperty("supplier")
    private Supplier supplier;

    public Coffee() {}

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

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


    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    @Override
    public String toString() {
        return "Coffee{" +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", sold=" + sold +
                ", total=" + total +
                ", supplier=" + supplier +
                '}';
    }
}
