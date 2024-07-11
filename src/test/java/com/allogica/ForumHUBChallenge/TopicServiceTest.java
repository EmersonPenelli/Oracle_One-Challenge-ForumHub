package com.allogica.ForumHUBChallenge;

import com.allogica.ForumHUBChallenge.Model.Entities.Answer;
import com.allogica.ForumHUBChallenge.Model.Entities.DTOs.CreateAnswerDTO;
import com.allogica.ForumHUBChallenge.Model.Entities.DTOs.TopicDTO;
import com.allogica.ForumHUBChallenge.Model.Entities.Enums.TopicStatus;
import com.allogica.ForumHUBChallenge.Model.Entities.Topic;
import com.allogica.ForumHUBChallenge.Model.Entities.User;
import com.allogica.ForumHUBChallenge.Model.Repositories.AnswerRepository;
import com.allogica.ForumHUBChallenge.Model.Repositories.TopicRepository;
import com.allogica.ForumHUBChallenge.Model.Services.TopicService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TopicServiceTest {

    @Mock
    private TopicRepository topicRepository;

    @Mock
    private AnswerRepository answerRepository;

    @InjectMocks
    private TopicService topicService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTopic() {
        TopicDTO topicDTO = new TopicDTO("Test Title", "Test Description");
        User user = new User(); // configure user as needed

        Topic topic = new Topic("Test Title", "Test Description", user);
        when(topicRepository.save(any(Topic.class))).thenReturn(topic);

        Topic createdTopic = topicService.createTopic(topicDTO, user);
        assertEquals("Test Title", createdTopic.getTitle());
        verify(topicRepository, times(1)).save(any(Topic.class));
    }

    @Test
    void testGetTopic() {
        Topic topic = new Topic();
        topic.setEnabled(true);
        when(topicRepository.findByIdAndEnabledTrue(anyLong())).thenReturn(Optional.of(topic));

        Topic foundTopic = topicService.getTopic(1L);
        assertNotNull(foundTopic);
        verify(topicRepository, times(2)).findByIdAndEnabledTrue(1L);
    }

    @Test
    void testGetTopics() {
        Topic topic1 = new Topic();
        Topic topic2 = new Topic();
        topic1.setEnabled(true);
        topic2.setEnabled(true);
        topic1.setId(1L);
        topic2.setId(2L);
        topic1.setTitle("Test Title 1");
        topic2.setTitle("Test Title 2");
        topic1.setCreationDate(LocalDateTime.of(2021, 9, 1, 0, 0, 0, 0));
        topic2.setCreationDate(LocalDateTime.of(2021, 9, 2, 0, 0, 0, 0));
        topic1.setStatus(TopicStatus.NOT_ANSWERED);
        topic2.setStatus(TopicStatus.NOT_ANSWERED);
        when(topicRepository.findAllByEnabledTrue()).thenReturn(Arrays.asList(topic1, topic2));

        assertEquals(2, topicService.getTopics().length);
        verify(topicRepository, times(1)).findAllByEnabledTrue();
    }

    @Test
    void testUpdateTopic() {
        Topic topic = new Topic();
        User user = new User();
        topic.setAuthor(user);
        topic.setStatus(TopicStatus.UNDER_DISCUSSION);

        TopicDTO topicDTO = new TopicDTO("Updated Title", "Updated Description");
        when(topicRepository.findByIdAndEnabledTrue(anyLong())).thenReturn(Optional.of(topic));

        Topic updatedTopic = topicService.updateTopic(1L, topicDTO, user);
        assertEquals("Updated Title", updatedTopic.getTitle());
        assertEquals("Updated Description", updatedTopic.getMessage());
    }

    @Test
    void testDeleteTopic() {
        Topic topic = new Topic();
        User user = new User();
        topic.setAuthor(user);
        when(topicRepository.findByIdAndEnabledTrue(anyLong())).thenReturn(Optional.of(topic));

        topicService.deleteTopic(1L, user);
        assertFalse(topic.isEnabled());
    }

    @Test
    void testAddAnswer() {
        Topic topic = new Topic();
        topic.setStatus(TopicStatus.NOT_ANSWERED);
        topic.setAnswers(new ArrayList<>());
        User user = new User();
        CreateAnswerDTO createAnswerDTO = new CreateAnswerDTO("Answer message");
        Answer answer = new Answer(createAnswerDTO, topic, user);

        when(topicRepository.findByIdAndEnabledTrue(anyLong())).thenReturn(Optional.of(topic));
        when(answerRepository.save(any(Answer.class))).thenReturn(answer);

        Answer addedAnswer = topicService.addAnswer(1L, createAnswerDTO, user);
        assertEquals("Answer message", addedAnswer.getMessage());
    }


    @Test
    void testDeleteAnswer() {
        Topic topic = new Topic();
        topic.setStatus(TopicStatus.UNDER_DISCUSSION);
        User user = new User();
        Answer answer = new Answer();
        answer.setAuthor(user);
        answer.setTopic(topic);
        when(topicRepository.findByIdAndEnabledTrue(anyLong())).thenReturn(Optional.of(topic));
        when(answerRepository.findByIdAndEnabledTrue(anyLong())).thenReturn(Optional.of(answer));

        topicService.deleteAnswer(1L, 1L, user);
        assertFalse(answer.isEnabled());
    }

    @Test
    void testUpdateAnswer() {
        User user = new User();
        Topic topic = new Topic();
        topic.setId(1L);
        topic.setStatus(TopicStatus.UNDER_DISCUSSION);
        Answer answer = new Answer();
        answer.setAuthor(user);
        answer.setTopic(topic);
        topic.setAnswers(new ArrayList<>());
        topic.addAnswer(answer);
        CreateAnswerDTO createAnswerDTO = new CreateAnswerDTO("Updated message");

        when(answerRepository.findByIdAndEnabledTrue(anyLong())).thenReturn(Optional.of(answer));

        Answer updatedAnswer = topicService.updateAnswer(1L, 1L, createAnswerDTO, user);
        assertEquals("Updated message", updatedAnswer.getMessage());
    }

    @Test
    void testFinishTopic() {
        User user = new User();
        Topic topic = new Topic();
        topic.setAuthor(user);
        topic.setStatus(TopicStatus.UNDER_DISCUSSION);
        when(topicRepository.findByIdAndEnabledTrue(anyLong())).thenReturn(Optional.of(topic));

        Topic finishedTopic = topicService.finishTopic(1L, user);
        assertEquals(TopicStatus.FINISHED, finishedTopic.getStatus());
    }



}
