package kg.devcats.coffee_shop.entity.postgres;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "cof_photo", nullable = true)
    @JsonProperty("photo")
    private String photo;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "cof_sup_id", nullable = false)
    @JsonProperty("supplier")
    private Supplier supplier;

    @OneToMany(mappedBy = "coffee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CofInventory> inventories;

    public Coffee() {
        inventories = new ArrayList<>();
    }

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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public List<CofInventory> getInventories() {
        return inventories;
    }

    public void setInventories(List<CofInventory> inventories) {
        this.inventories = inventories;
    }

    @Override
    public String toString() {
        return "coffee{" +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", sold=" + sold +
                ", total=" + total +
                ", supplier=" + supplier +
                ", photo=" + photo +
                '}';
    }
}
