package com.allogica.ForumHUBChallenge.Model.Entities.DTOs;

import com.allogica.ForumHUBChallenge.Model.Entities.Answer;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.format.DateTimeFormatter;

public record CreateAnswerDTO(
        @NotBlank
        String message
) {

    public static CreateAnswerDTO fromAnswer(Answer answer) {
        return new CreateAnswerDTO(
                answer.getMessage()
        );
    }
}
