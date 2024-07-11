package com.allogica.ForumHUBChallenge.Model.Entities.DTOs;

import com.allogica.ForumHUBChallenge.Model.Entities.Topic;
import jakarta.validation.constraints.NotBlank;

import java.time.format.DateTimeFormatter;

public record TopicDTO (
        @NotBlank
        String title,
        @NotBlank
        String description
) {

}
