package kg.devcats.coffee_shop.repository.postgres;

import kg.devcats.coffee_shop.entity.postgres.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateServiceJPA extends JpaRepository<State, String> {
}
