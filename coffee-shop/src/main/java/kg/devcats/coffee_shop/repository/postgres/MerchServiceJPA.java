package kg.devcats.coffee_shop.repository.postgres;

import kg.devcats.coffee_shop.entity.postgres.Merch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchServiceJPA extends JpaRepository<Merch, Long> {
}
