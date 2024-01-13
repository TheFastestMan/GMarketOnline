package ru.rail.gmarketonline.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.rail.gmarketonline.entity.Gender;
import ru.rail.gmarketonline.entity.Role;
@Builder
@Getter
@Setter
@ToString
public class UserDto {
    private Long id;

    @NotEmpty(message = "Name should not be empty")
    private String username;

    @Email(message = "Email should be valid")
    @NotEmpty(message = "Email should not be empty")
    private String email;

    @NotNull(message = "Role should not be empty")
    private Role role;

    @NotNull(message = "Gender should not be empty")
    private Gender gender;

    @Size(min = 5, message = "Password should be not less than 5 characters")
    @NotEmpty(message = "Password should not be empty")
    private String password;


}
