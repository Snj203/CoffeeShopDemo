package kg.devcats.coffee_shop.repository.storage_to_file_system;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {
    void init();

    Path store(MultipartFile file);

    Stream<Path> loadAll();

    Path load(String filename);

    Resource loadAsResource(String filename);

    boolean delete(String filename);

    boolean update(String filename, MultipartFile photo);

    void deleteAll();

}
