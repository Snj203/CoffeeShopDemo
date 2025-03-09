package kg.devcats.coffee_shop.repository.jpa;

import kg.devcats.coffee_shop.entity.security.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserServiceJPA extends JpaRepository<User, String> {
}
