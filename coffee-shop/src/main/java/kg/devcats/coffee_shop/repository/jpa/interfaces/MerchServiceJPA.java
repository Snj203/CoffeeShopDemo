package kg.devcats.coffee_shop.repository.jpa.interfaces;

import kg.devcats.coffee_shop.entity.Merch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchServiceJPA extends JpaRepository<Merch, Long> {
}
