package com.empmarket.employmentmarketplace.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDto {

    @NotBlank(message = "Username must not be empty")
    private String email;

    @NotBlank(message = "Password must not be empty")
    private String password;
}
