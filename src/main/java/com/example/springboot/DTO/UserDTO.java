package com.example.springboot.DTO;

import jakarta.validation.constraints.NotBlank;

public record UserDTO(@NotBlank String name, @NotBlank String email, @NotBlank String cnpj) {
}
