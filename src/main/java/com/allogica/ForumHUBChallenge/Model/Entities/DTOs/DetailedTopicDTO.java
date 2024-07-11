package com.allogica.ForumHUBChallenge.Model.Entities.DTOs;

import com.allogica.ForumHUBChallenge.Model.Entities.Topic;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.format.DateTimeFormatter;
import java.util.Objects;

public record DetailedTopicDTO(
        Long id,
        String title,
        String message,
        @JsonProperty("author") String author,
        String status,
        @JsonProperty("created_at") String createdAt,
        ResponseAnswerDTO[] answers

) {
    public static DetailedTopicDTO fromTopic(Topic topic){
        return new DetailedTopicDTO(
                topic.getId(),
                topic.getTitle(),
                topic.getMessage(),
                topic.getAuthor().getUsername(),
                topic.getStatus().toString(),
                topic.getCreationDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")),
                topic.getAnswers().stream().map(ResponseAnswerDTO::fromAnswer).filter(Objects::nonNull).toArray(ResponseAnswerDTO[]::new)
        );

    }

}
