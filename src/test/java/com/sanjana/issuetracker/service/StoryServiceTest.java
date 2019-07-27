package com.sanjana.issuetracker.service;

import com.sanjana.issuetracker.exception.DeveloperNotFoundException;
import com.sanjana.issuetracker.exception.InvalidUpdateException;
import com.sanjana.issuetracker.exception.StoryNotFoundException;
import com.sanjana.issuetracker.model.Developer;
import com.sanjana.issuetracker.model.Issue;
import com.sanjana.issuetracker.model.Story;
import com.sanjana.issuetracker.model.StoryStatus;
import com.sanjana.issuetracker.repository.DeveloperRepository;
import com.sanjana.issuetracker.repository.StoryRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
public class StoryServiceTest {

    @Mock
    private StoryRepository storyRepository;

    @Mock
    private DeveloperRepository developerRepository;

    @InjectMocks
    private StoryService storyService;

    @Test
    public void createStoryTest(){
        Issue issue = new Issue();
        issue.setId(0);
        issue.setCreationDate(new Date());
        story.setIssue(issue);
        story.setEstimation(2);
        storyService.createStory(story);
        Mockito.verify(storyRepository).createStory(story);
    }

    @Test
    public void updateExistingStoryTest(){
        setUp();
        Issue issue = new Issue();
        issue.setId(0);
        story.setIssue(issue);
        story.setStatus(StoryStatus.COMPLETED);
        storyService.updateStory(story);
        Assert.assertEquals(StoryStatus.COMPLETED,
                stories.stream()
                        .filter(storyItem -> storyItem.getIssue().getId().equals(story.getIssue().getId()))
                        .findFirst()
                        .get().getStatus());
    }

    @Test(expected = StoryNotFoundException.class)
    public void updateNonExistingStoryTest(){
        setUp();
        Story story = new Story();
        Issue issue = new Issue();
        issue.setId(10);
        story.setIssue(issue);
        story.setStatus(StoryStatus.NEW);
        storyService.updateStory(story);
    }

    @Test(expected = InvalidUpdateException.class)
    public void updateCreationDateStoryTest(){
        setUp();
        Issue issue = new Issue();
        issue.setId(0);
        issue.setCreationDate(new Date());
        story.setIssue(issue);
        story.setStatus(StoryStatus.NEW);
        storyService.updateStory(story);
    }

    @Test(expected = DeveloperNotFoundException.class)
    public void updateInvalidDeveloperBugTest(){
        setUp();
        Developer developer = new Developer();
        developer.setName("Prashu");
        Issue issue = new Issue();
        issue.setId(0);
        issue.setCreationDate(new Date());
        issue.setDeveloper(developer);
        story.setIssue(issue);
        story.setStatus(StoryStatus.NEW);
        storyService.updateStory(story);
    }

    private Story story = new Story();

    private Set<Story> stories = new HashSet<>();

    private Set<Developer> developers = new HashSet<>();

    private void setUp(){
        setUpStories();
        Mockito.when(storyRepository.getStories()).thenReturn(stories);
        Mockito.when(developerRepository.getDevelopers()).thenReturn(developers);
    }

    private void setUpStories(){
        for (int i = 0; i < 6 ; i++) {
            story = new Story();
            story.setIssue(setUpIssueDetails(i));
            story.setStatus(setStatusDetails(i));
            stories.add(story);
        }
    }

    private Issue setUpIssueDetails(int i){
        Issue issueDetails = new Issue();
        Developer developer = new Developer();
        developer.setName("Sanju"+i);
        developers.add(developer);
        issueDetails.setId(i);
        issueDetails.setTitle("Test Title"+i);
        issueDetails.setDescription("Test Description"+i);
        issueDetails.setDeveloper(developer);
        return issueDetails;
    }

    private StoryStatus setStatusDetails(int i){
        if (i == 0 || i == 2){
            return StoryStatus.NEW;
        } else if (i == 1 || i == 3){
            return StoryStatus.ESTIMATED;
        } else {
            return StoryStatus.COMPLETED;
        }
    }

}
