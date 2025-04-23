package kg.devcats.coffee_shop.repository.postgres;

import kg.devcats.coffee_shop.entity.postgres.CoffeeHouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeHouseRepositoryJPA extends JpaRepository<CoffeeHouse, Long> {
}
