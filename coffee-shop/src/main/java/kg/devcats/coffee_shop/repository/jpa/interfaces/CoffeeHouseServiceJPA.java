package kg.devcats.coffee_shop.repository.jpa.interfaces;

import kg.devcats.coffee_shop.entity.CoffeeHouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeHouseServiceJPA extends JpaRepository<CoffeeHouse, Long> {
}
