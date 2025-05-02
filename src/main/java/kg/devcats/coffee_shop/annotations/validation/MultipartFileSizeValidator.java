package kg.devcats.coffee_shop.annotations.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class MultipartFileSizeValidator implements ConstraintValidator<MultipartFileSizeValid, MultipartFile> {

    private static final String ERROR_MESSAGE = "File too Large.";

    private long maxSize;

    @Override
    public void initialize(MultipartFileSizeValid constraintAnnotation) {
        this.maxSize = constraintAnnotation.maxSize();
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null || file.isEmpty()) {
            return false;
        }

        if (file.getSize() > maxSize) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("File size must be less than 5mb")
                    .addConstraintViolation();
            return false;
        }

        if (!file.getContentType().equals("image/png")) {
            context.buildConstraintViolationWithTemplate("Unsupported file type")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
