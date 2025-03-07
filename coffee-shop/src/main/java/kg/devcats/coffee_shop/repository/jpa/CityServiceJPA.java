package kg.devcats.coffee_shop.repository.jpa;

import kg.devcats.coffee_shop.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityServiceJPA extends JpaRepository<City, String> {
}
