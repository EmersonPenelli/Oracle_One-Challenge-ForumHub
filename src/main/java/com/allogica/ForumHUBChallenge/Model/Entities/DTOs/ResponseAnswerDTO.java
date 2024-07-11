package com.allogica.ForumHUBChallenge.Model.Entities.DTOs;

import com.allogica.ForumHUBChallenge.Model.Entities.Answer;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.format.DateTimeFormatter;

public record ResponseAnswerDTO(
        @NotNull
        Long id,
        @NotBlank
        String message,
        @NotBlank
        String authorName,
        String creationDate,
        @NotNull
        Long topicId
) {

    public static ResponseAnswerDTO fromAnswer(Answer answer) {
        if (answer.isEnabled()){
            return new ResponseAnswerDTO(
                    answer.getId(),
                    answer.getMessage(),
                    answer.getAuthor().getUsername(),
                    answer.getCreationDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")),
                    answer.getTopic().getId()
            );
        }
        else {
            return null;
        }
    }
}
