package com.itsmerizzi.payment_system.dto;

import com.itsmerizzi.payment_system.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserCreateRequest(
        @NotNull(message = "Property 'name' cannot be null")
        @NotBlank(message = "Property 'name' cannot be empty")
        @Size(max = 124, message = "Property 'name' has a maximum of 124 characters")
        String name,

        @NotNull(message = "Property 'email' cannot be null")
        @NotBlank(message = "Property 'email' cannot be empty")
        @Email
        String email,

        @NotNull(message = "Property 'password' cannot be null")
        @NotBlank(message = "Property 'password' cannot be empty")
        @Size(min = 8, message = "Property 'password' must have at least 8 characters")
        String password,

        @NotNull(message = "Property 'role' cannot be null")
        @NotBlank(message = "Property 'role' cannot be empty")
        String role
) {

    public User toModel() {
        return new User(name, email, password, role);
    }

}
