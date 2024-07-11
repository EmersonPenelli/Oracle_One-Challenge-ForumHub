package com.allogica.ForumHUBChallenge.Model.Entities.DTOs;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;

public record UserDTO(
        @NotBlank
        String password,
        @NotBlank
        @JsonAlias("user_name")
        String userName) {
}
