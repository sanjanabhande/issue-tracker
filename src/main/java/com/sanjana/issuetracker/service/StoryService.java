package com.sanjana.issuetracker.service;

import com.sanjana.issuetracker.exception.BigStoryException;
import com.sanjana.issuetracker.exception.DeveloperAssignmentNotAllowedException;
import com.sanjana.issuetracker.exception.DeveloperNotFoundException;
import com.sanjana.issuetracker.exception.InvalidUpdateException;
import com.sanjana.issuetracker.exception.StoryNotFoundException;
import com.sanjana.issuetracker.model.Story;
import com.sanjana.issuetracker.model.StoryStatus;
import com.sanjana.issuetracker.repository.DeveloperRepository;
import com.sanjana.issuetracker.repository.IssueRepository;
import com.sanjana.issuetracker.repository.StoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Service
public class StoryService {

    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private DeveloperRepository developerRepository;

    public void createStory(final Story story){
        if (story.getEstimation()!=null){
            story.setStatus(StoryStatus.ESTIMATED);
        } else {
            story.setStatus(StoryStatus.NEW);
        }
        if (story.getIssue().getDeveloper()!=null && story.getIssue().getDeveloper().getName()!=null && story.getIssue().getDeveloper().getName()!=""){
            throw new DeveloperAssignmentNotAllowedException("Developer assignment not allowed at the time of creation");
        }
        story.getIssue().setId(++IssueRepository.issueId);
        story.getIssue().setCreationDate(new Date());
        checkStoryEstimate(story);
        storyRepository.createStory(story);
    }

    public Set<Story> getStories(){
        return storyRepository.getStories();
    }

    public void updateStory(final Story story){
        final Optional<Story> updateStoryById = storyRepository.getStories()
                .stream()
                .filter(updateStory -> updateStory.getIssue().getId()==story.getIssue().getId())
                .findFirst();
        checkStoryPresent(updateStoryById);
        checkStoryEstimate(story);
        updateIssueDetails(story,updateStoryById);
        updateStoryDetails(story,updateStoryById);
    }

    private void checkStoryPresent(final Optional<Story> updateStoryById){
        if (!updateStoryById.isPresent()){
            throw new StoryNotFoundException("Story to be updated does not exist");
        }
    }

    private void updateIssueDetails(final Story story, final Optional<Story> updateStoryById){
        if (story.getIssue().getDeveloper()!=null && story.getIssue().getDeveloper().getName()!=null && story.getIssue().getDeveloper().getName()!=""){
            checkDeveloperAvailable(story);
            updateStoryById.get().getIssue().setDeveloper(story.getIssue().getDeveloper());
        }
        if (story.getIssue().getTitle()!=null){
            updateStoryById.get().getIssue().setTitle(story.getIssue().getTitle());
        }
        if (story.getIssue().getDescription()!=null){
            updateStoryById.get().getIssue().setDescription(story.getIssue().getDescription());
        }
        if (story.getIssue().getCreationDate()!=null){
            throw new InvalidUpdateException("Creation date of the story cannot be updated");
        }
    }

    private void checkDeveloperAvailable(final Story story){
        if (!developerRepository.getDevelopers().contains(story.getIssue().getDeveloper())){
            throw new DeveloperNotFoundException("Developer not found to assign the story");
        }
    }

    private void checkStoryEstimate(final Story story){
        if(story.getEstimation()!=null && story.getEstimation()>10){
            throw new BigStoryException("story estimate more than 10 is not allowed. please split the story");
        }
    }

    private void updateStoryDetails(final Story story, final Optional<Story> updateStoryById){
        if (story.getStatus()!=null){
            updateStoryById.get().setStatus(story.getStatus());
        }
        if (story.getEstimation()!=null){
            updateStoryById.get().setEstimation(story.getEstimation());
            updateStoryById.get().setStatus(StoryStatus.ESTIMATED);
        }
    }
}
