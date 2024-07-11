package com.allogica.ForumHUBChallenge.Model.Entities.DTOs;

import com.allogica.ForumHUBChallenge.Model.Entities.Topic;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.time.format.DateTimeFormatter;

public record ResumedTopicDTO(
        @NotNull
        Long id,
        String title,
        String status,
        @JsonProperty("created_at") String createdAt) {

    public static ResumedTopicDTO fromTopic(Topic topic) {
        return new ResumedTopicDTO(
                topic.getId(),
                topic.getTitle(),
                topic.getStatus().toString(),
                topic.getCreationDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
        );

    }
}


