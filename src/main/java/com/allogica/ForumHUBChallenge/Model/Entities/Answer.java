package com.allogica.ForumHUBChallenge.Model.Entities;

import com.allogica.ForumHUBChallenge.Model.Entities.DTOs.CreateAnswerDTO;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@EqualsAndHashCode(of = "id")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    private LocalDateTime creationDate;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;

    private boolean enabled;

    public Answer(CreateAnswerDTO createAnswerDTO, Topic topic, User author) {
        this.message = createAnswerDTO.message();
        this.author = author;
        this.topic = topic;
        this.creationDate = LocalDateTime.now();
        this.enabled = true;
    }


}
