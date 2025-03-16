package kg.devcats.coffee_shop.repository.postgres;

import kg.devcats.coffee_shop.entity.postgres.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeServiceJPA extends JpaRepository<Coffee, String> {
}
