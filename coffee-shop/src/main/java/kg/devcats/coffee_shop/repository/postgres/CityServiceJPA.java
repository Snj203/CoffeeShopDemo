package kg.devcats.coffee_shop.repository.postgres;

import kg.devcats.coffee_shop.entity.postgres.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityServiceJPA extends JpaRepository<City, String> {
}
