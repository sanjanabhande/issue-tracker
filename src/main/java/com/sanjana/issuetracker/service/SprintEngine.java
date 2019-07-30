package com.sanjana.issuetracker.service;

import com.sanjana.issuetracker.model.Developer;
import com.sanjana.issuetracker.model.Sprint;
import com.sanjana.issuetracker.model.Story;
import com.sanjana.issuetracker.repository.DeveloperRepository;
import com.sanjana.issuetracker.repository.StoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Component
public class SprintEngine {

    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private DeveloperRepository developerRepository;

    public List<Sprint> plan(){
        final List<Sprint> sprints = new ArrayList<>();
        final List<Story> availableStories = new ArrayList<>();
        availableStories.addAll(storyRepository.getStories());
        Collections.sort(availableStories);
        int weekCount = 1;
        while (!availableStories.isEmpty()){
            Sprint sprint = new Sprint("Week"+weekCount, prepareSprint(availableStories));
            updateStoryDataInCache(availableStories);
            weekCount++;
            sprints.add(sprint);
        }
        return sprints;
    }

    private List<Story> prepareSprint(final List<Story> availableStories){
        final List<Story> sprintStories = new ArrayList<>();
        final Set<Developer> availableDevelopers = developerRepository.getDevelopers();
        for(Developer developer: availableDevelopers){
            sprintStories.addAll(allocateDeveloper(developer, availableStories));
        }
        return sprintStories;
    }

    private List<Story> allocateDeveloper(final Developer developer, final List<Story> storiesToAssign){
        final List<Story> sprintStories = new ArrayList<>();
        int remainingPoints = 10;
        final Iterator<Story> storyIterator = storiesToAssign.iterator();
        while (storyIterator.hasNext()){
            final Story story = storyIterator.next();
            if(story.getEstimation()>remainingPoints) {
                break;
            }
            story.getIssue().setDeveloper(developer);
            remainingPoints = remainingPoints - story.getEstimation();
            sprintStories.add(story);
            storyIterator.remove();
        }
        return sprintStories;
    }

    private void updateStoryDataInCache(final List<Story> stories){
        storyRepository.createAllStories(stories);
    }
}
