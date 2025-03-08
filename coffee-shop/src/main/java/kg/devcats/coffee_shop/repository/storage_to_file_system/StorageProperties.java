package kg.devcats.coffee_shop.repository.storage_to_file_system;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageProperties {

    private String location = "upload-dir";
    private String preloadLocation = "src/main/resources/static/images/photo";

    public String getLocation() {
        return location;
    }

    public String getPreloadLocation() {
        return preloadLocation;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
