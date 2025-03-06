package kg.devcats.coffee_shop.repository.jpa.interfaces;

import kg.devcats.coffee_shop.entity.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeServiceJPA extends JpaRepository<Coffee, Long> {
}
