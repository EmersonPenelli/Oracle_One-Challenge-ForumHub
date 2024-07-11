package com.allogica.ForumHUBChallenge.Model.Entities.Enums;

import lombok.Getter;

@Getter
public enum TopicStatus {

    NOT_ANSWERED("Not answered"),
    UNDER_DISCUSSION("Under discussion"),
    FINISHED("Finished");

    private final String description;

    TopicStatus(String description) {
        this.description = description;
    }

}
