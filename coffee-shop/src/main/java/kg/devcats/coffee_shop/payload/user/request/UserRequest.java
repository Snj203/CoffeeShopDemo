package kg.devcats.coffee_shop.payload.user.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record UserRequest (
        @NotNull
        @NotBlank()
        @Size(min = 2, max = 50)
        String userName,

        @NotNull
        @NotBlank()
        @Size(min = 2, max = 50)
        String password,

        @NotNull
        @Size(min = 2, max = 10)
        List<String> role
){
}
