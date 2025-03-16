package kg.devcats.coffee_shop.repository.postgres;

import kg.devcats.coffee_shop.entity.postgres.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierServiceJPA extends JpaRepository<Supplier, Long> {
}
