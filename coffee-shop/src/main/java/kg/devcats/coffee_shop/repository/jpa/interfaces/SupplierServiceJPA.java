package kg.devcats.coffee_shop.repository.jpa.interfaces;

import kg.devcats.coffee_shop.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierServiceJPA extends JpaRepository<Supplier, Long> {
}
