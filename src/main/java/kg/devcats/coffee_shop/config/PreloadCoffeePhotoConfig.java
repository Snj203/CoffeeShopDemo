package kg.devcats.coffee_shop.config;

import kg.devcats.coffee_shop.entity.postgres.Coffee;
import kg.devcats.coffee_shop.repository.postgres.CoffeeRepositoryJPA;
import kg.devcats.coffee_shop.repository.storage_to_file_system.StorageException;
import kg.devcats.coffee_shop.repository.storage_to_file_system.StorageProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class PreloadCoffeePhotoConfig {
    @Value("${spring.storage.delete-on-start}")
    boolean deleteOnStart;

    private final Path rootLocation;
    @Value("${spring.storage.default-photo-name}")
    private String defaultPhotoName;

    public PreloadCoffeePhotoConfig(StorageProperties properties) {

        if(properties.getPreloadLocation().trim().length() == 0){
            throw new StorageException("File upload location can not be Empty.");
        }
        this.rootLocation = Paths.get(properties.getPreloadLocation());
    }

    @Bean
    public CommandLineRunner initPhoto(CoffeeRepositoryJPA coffeeRepositoryJPA) {
        return (args) -> {
            if(deleteOnStart) {
                for(Coffee coffee : coffeeRepositoryJPA.findAll()){
                    coffee.setPhoto(rootLocation.resolve(defaultPhotoName).normalize().toAbsolutePath().toString());
                    coffeeRepositoryJPA.save(coffee);
                }
            }
        };
    }
}
