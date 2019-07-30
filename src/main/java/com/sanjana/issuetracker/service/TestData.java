package com.sanjana.issuetracker.service;

import com.sanjana.issuetracker.model.Developer;
import com.sanjana.issuetracker.model.Issue;
import com.sanjana.issuetracker.model.Story;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.Random;

@Component
public class TestData {

    @Autowired
    private DeveloperService developerService;

    @Autowired
    private StoryService storyService;

    @PostConstruct
    public void load(){
        loadDevelopers();
        loadStories();
    }

    private void loadStories(){
        for(int i=0; i<20; i++) {
            Story story = new Story();
            Issue issue = new Issue();
            issue.setTitle("Title "+i);
            issue.setDescription("Description "+i);
            issue.setCreationDate(new Date());
            story.setIssue(issue);
            Random random = new Random();
            story.setEstimation(random.nextInt(8)+1);
            storyService.createStory(story);
        }
    }

    private void loadDevelopers(){
        for(int i=1; i<=5; i++) {
            Developer developer = new Developer();
            developer.setName("Developer "+i);
            developerService.createDeveloper(developer);
        }
    }
}
