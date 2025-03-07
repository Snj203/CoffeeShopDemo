package kg.devcats.coffee_shop.repository.jpa;

import kg.devcats.coffee_shop.entity.CofInventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CofInventoryServiceJPA extends JpaRepository<CofInventory, Long> {
}
