package com.allogica.ForumHUBChallenge.Model.Services;

import com.allogica.ForumHUBChallenge.Model.CustomExceptions.ValidationException;
import com.allogica.ForumHUBChallenge.Model.Entities.Answer;
import com.allogica.ForumHUBChallenge.Model.Entities.DTOs.CreateAnswerDTO;
import com.allogica.ForumHUBChallenge.Model.Entities.DTOs.ResumedTopicDTO;
import com.allogica.ForumHUBChallenge.Model.Entities.DTOs.TopicDTO;
import com.allogica.ForumHUBChallenge.Model.Entities.Enums.TopicStatus;
import com.allogica.ForumHUBChallenge.Model.Entities.Topic;
import com.allogica.ForumHUBChallenge.Model.Entities.User;
import com.allogica.ForumHUBChallenge.Model.Repositories.AnswerRepository;
import com.allogica.ForumHUBChallenge.Model.Repositories.TopicRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Transactional
    public Topic createTopic(TopicDTO topicDTO, User user) {
        Topic topic = new Topic(topicDTO.title(), topicDTO.description(), user);
        topicRepository.save(topic);
        return topic;
    }

    public Topic getTopic(Long id) {
        Topic topic = topicRepository.findByIdAndEnabledTrue(id).orElseThrow();

        return topicRepository.findByIdAndEnabledTrue(id).orElseThrow();
    }

    public ResumedTopicDTO[] getTopics() {
        return topicRepository.findAllByEnabledTrue().stream().map(ResumedTopicDTO::fromTopic).toArray(ResumedTopicDTO[]::new);
    }

    @Transactional
    public Topic updateTopic(Long id, TopicDTO topicDTO, User user) {
        Topic topic = getTopic(id);
        if (topic.getStatus().equals(TopicStatus.FINISHED)) {
            throw new ValidationException("Topic is already finished");
        }
        if (topic.getAuthor().equals(user)) {
            topic.setTitle(topicDTO.title());
            topic.setMessage(topicDTO.description());
            return topic;
        }
        else {
            throw new ValidationException("User is not the author of the topic");
        }
    }

    @Transactional
    public void deleteTopic (Long id, User user) {
        Topic topic = getTopic(id);
        if (topic.getAuthor().equals(user)) {
            topic.setEnabled(false);
        }
        else {
            throw new ValidationException("User is not the author of the topic");
        }
    }

    @Transactional
    public Answer addAnswer(Long id, CreateAnswerDTO createAnswerDTO, User author) {
        Topic topic = getTopic(id);
        if (topic.getStatus().equals(TopicStatus.FINISHED)) {
            throw new ValidationException("Topic is already finished");
        }
        Answer answer = new Answer(createAnswerDTO, topic, author);
        answerRepository.save(answer);
        topic = topic.addAnswer(answer);
        if (topic.getStatus() == TopicStatus.NOT_ANSWERED) {
            topic.setStatus(TopicStatus.UNDER_DISCUSSION);
        }
        return topic.getLastAnswer();
    }

    @Transactional
    public void deleteAnswer(Long id, Long answerId, User user) {
        Topic topic = getTopic(id);
        Answer answer = answerRepository.findByIdAndEnabledTrue(answerId).orElseThrow();
        if (answer.getAuthor().equals(user)) {
            if (answer.getTopic().getStatus().equals(TopicStatus.FINISHED)) {
                throw new ValidationException("Topic is already finished");
            }
            answer.setEnabled(false);
        }
        else {
            throw new ValidationException("User is not the author of the answer");
        }
    }

    @Transactional
    public Answer updateAnswer(Long id, Long answerId, CreateAnswerDTO createAnswerDTO, User user) {
        Answer answer = answerRepository.findByIdAndEnabledTrue(answerId).orElseThrow();
        if (answer.getAuthor().equals(user)) {
            if (Objects.equals(answer.getTopic().getId(), id)) {
                if (answer.getTopic().getStatus().equals(TopicStatus.FINISHED)) {
                    throw new ValidationException("Topic is already finished");
                }
                answer.setMessage(createAnswerDTO.message());
                return answer;
            }
            else {
                throw new ValidationException("Answer does not belong to the topic");
            }
        }
        else {
            throw new ValidationException("User is not the author of the answer");
        }
    }

    @Transactional
    public Topic finishTopic(Long id, User user) {
        Topic topic = getTopic(id);
        if (topic.getAuthor().equals(user)) {
            if (topic.getStatus() == TopicStatus.FINISHED) {
                throw new ValidationException("Topic is already finished");
            }
            topic.setStatus(TopicStatus.FINISHED);
            return topic;
        }
        else {
            throw new ValidationException("User is not the author of the topic");
        }
    }
}
