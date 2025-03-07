package kg.devcats.coffee_shop.repository.jpa;

import kg.devcats.coffee_shop.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateServiceJPA extends JpaRepository<State, String> {
}
