package com.sanjana.issuetracker.repository;

import com.sanjana.issuetracker.model.Story;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository
public class StoryRepository {

    private Set<Story> stories = new HashSet<>();

    public void createStory(Story story){
        stories.add(story);
    }

    public Set<Story> getStories(){
        return stories;
    }

}
