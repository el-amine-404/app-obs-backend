package org.obs.app.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.obs.app.model.Gender;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    
    @NotBlank(message = "Id must not be blank")
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

    @NotBlank
    @Digits(integer = 3, fraction = 0, message = "Invalid age. Maximum valid number for age is 3 digits.")
    private int age;

    @NotBlank
    private Gender gender;
}
