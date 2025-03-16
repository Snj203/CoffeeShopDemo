package kg.devcats.coffee_shop.repository.h2;

import kg.devcats.coffee_shop.entity.h2.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserServiceJPA extends JpaRepository<User, String> {
}
