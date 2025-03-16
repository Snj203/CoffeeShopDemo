package kg.devcats.coffee_shop.repository.postgres;

import kg.devcats.coffee_shop.entity.postgres.CofInventory;
import kg.devcats.coffee_shop.entity.postgres.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CofInventoryServiceJPA extends JpaRepository<CofInventory, Long> {
    void deleteByCoffee(Coffee coffee);

    List<CofInventory> findAllByCoffee(Coffee coffee);
}
