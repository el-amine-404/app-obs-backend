package org.obs.app.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.obs.app.model.Gender;
import org.obs.app.model.Role;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    
    @NotNull(message = "Id must not be blank")
    private Long id;

    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank
    @Size(min = 1, max = 50, message = "Username must be between 1 and 50 characters long")
    private String username;

    @NotBlank
    @Size(min = 1, max = 50, message = "FirstName must be between 1 and 50 characters long")
    private String firstName;

    @NotBlank
    @Size(min = 1, max = 50, message = "LastName must be between 1 and 50 characters long")
    private String lastName;


    @NotNull
    @Digits(integer = 3, fraction = 0, message = "Invalid age. Maximum valid number for age is 3 digits.")
    @Min(value = 0, message = "Age must not be less than 0")
    @Max(value = 999, message = "Age must not be more than 999")
    private int age;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Gender gender;
}
