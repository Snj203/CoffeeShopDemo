package kg.devcats.coffee_shop.repository.jpa;

import kg.devcats.coffee_shop.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseServiceJPA extends JpaRepository<Warehouse, Long> {
}
