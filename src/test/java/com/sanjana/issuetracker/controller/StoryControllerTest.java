package com.sanjana.issuetracker.controller;

import com.sanjana.issuetracker.service.StoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class StoryControllerTest {

    @Mock
    private StoryService storyService;

    @InjectMocks
    private StoryController storyController;

    @Test
    public void createStoryTest(){
        storyController.createStory(Mockito.any());
        Mockito.verify(storyService).createStory(Mockito.any());
    }

    @Test
    public void getStoriesTest(){
        storyController.getStories();
        Mockito.verify(storyService).getStories();
    }

    @Test
    public void updateStoryTest(){
        storyController.updateStory(Mockito.any());
        Mockito.verify(storyService).updateStory(Mockito.any());
    }

}
