package com.allogica.ForumHUBChallenge.Model.Repositories;

import com.allogica.ForumHUBChallenge.Model.Entities.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    List<Topic> findAllByEnabledTrue();
    Optional<Topic> findByIdAndEnabledTrue(Long id);
}
