package kg.devcats.coffee_shop.config;

import kg.devcats.coffee_shop.repository.storage_to_file_system.StorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageServiceConfig {

    @Value("${spring.storage.delete-on-start}")
    boolean deleteOnStart;

    @Bean
    public CommandLineRunner init(StorageService storageService) {
        return (args) -> {
            if(deleteOnStart) {
                storageService.deleteAll();
            }
            storageService.init();
        };
    }

}

