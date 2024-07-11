package com.allogica.ForumHUBChallenge.Model.Entities;

import com.allogica.ForumHUBChallenge.Model.Entities.DTOs.CreateAnswerDTO;
import com.allogica.ForumHUBChallenge.Model.Entities.Enums.TopicStatus;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@EqualsAndHashCode(of = "id")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String message;

    private LocalDateTime creationDate;

    private boolean enabled;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    public Topic(String title, String description, User author) {
        this.title = title;
        this.message = description;
        this.author = author;
        this.creationDate = LocalDateTime.now();
        this.status = TopicStatus.NOT_ANSWERED;
        this.answers = new ArrayList<>();
        this.enabled = true;
    }

    private TopicStatus status;

    @OneToMany(mappedBy = "topic")
    private List<Answer> answers;

    public Topic addAnswer(Answer answer) {
        answers.add(answer);
        return this;
    }

    public Answer getLastAnswer() {
        return answers.get(answers.size() - 1);
    }

}
