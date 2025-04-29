package kg.devcats.coffee_shop.repository.h2;

import kg.devcats.coffee_shop.entity.h2.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepositoryJPA extends JpaRepository<User, String> {
    Optional<User> findByVerificationCode(String verificationCode);

    Optional<User> findByEmail(String email);
}
