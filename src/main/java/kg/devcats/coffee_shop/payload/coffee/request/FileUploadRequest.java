package kg.devcats.coffee_shop.payload.coffee.request;

import jakarta.validation.constraints.NotNull;
import kg.devcats.coffee_shop.annotations.validation.MultipartFileSizeValid;
import org.springframework.web.multipart.MultipartFile;

public class FileUploadRequest {

    @NotNull
    @MultipartFileSizeValid
    private MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
