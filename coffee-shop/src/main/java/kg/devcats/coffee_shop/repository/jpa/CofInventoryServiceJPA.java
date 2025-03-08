package kg.devcats.coffee_shop.repository.jpa;

import jakarta.validation.constraints.NotNull;
import kg.devcats.coffee_shop.entity.CofInventory;
import kg.devcats.coffee_shop.entity.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CofInventoryServiceJPA extends JpaRepository<CofInventory, Long> {
    void deleteByCoffee(Coffee coffee);

    List<CofInventory> findAllByCoffee(Coffee coffee);
}
