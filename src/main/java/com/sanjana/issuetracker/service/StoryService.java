package com.sanjana.issuetracker.service;

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

    public void createStory(Story story){
        if (story.getEstimation()!=null){
            story.setStatus(StoryStatus.ESTIMATED);
        } else {
            story.setStatus(StoryStatus.NEW);
        }
        if (story.getIssue().getDeveloper()!=null && story.getIssue().getDeveloper().getName()!=null && story.getIssue().getDeveloper().getName()!=""){
            checkDeveloperAvailable(story);
        }
        story.getIssue().setId(++IssueRepository.issueId);
        story.getIssue().setCreationDate(new Date());
        storyRepository.createStory(story);
    }

    public Set<Story> getStories(){
        return storyRepository.getStories();
    }

    public void updateStory(Story story){
        Optional<Story> updateStoryById = storyRepository.getStories()
                .stream()
                .filter(updateStory -> updateStory.getIssue().getId()==story.getIssue().getId())
                .findFirst();
        checkStoryPresent(updateStoryById);
        updateIssueDetails(story,updateStoryById);
        updateStoryDetails(story,updateStoryById);
    }

    private void checkStoryPresent(Optional<Story> updateStoryById){
        if (!updateStoryById.isPresent()){
            throw new StoryNotFoundException("Story to be updated does not exist");
        }
    }

    private void updateIssueDetails(Story story, Optional<Story> updateStoryById){
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

    private void checkDeveloperAvailable(Story story){
        if (!developerRepository.getDevelopers().contains(story.getIssue().getDeveloper())){
            throw new DeveloperNotFoundException("Developer not found to assign the story");
        }
    }

    private void updateStoryDetails(Story story, Optional<Story> updateStoryById){
        if (story.getStatus()!=null){
            updateStoryById.get().setStatus(story.getStatus());
        }
        if (story.getEstimation()!=null){
            updateStoryById.get().setEstimation(story.getEstimation());
            updateStoryById.get().setStatus(StoryStatus.ESTIMATED);
        }
    }
}
