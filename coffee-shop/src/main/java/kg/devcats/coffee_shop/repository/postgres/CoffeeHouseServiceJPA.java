package kg.devcats.coffee_shop.repository.postgres;

import kg.devcats.coffee_shop.entity.postgres.CoffeeHouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeHouseServiceJPA extends JpaRepository<CoffeeHouse, Long> {
}
