package com.sanjana.issuetracker.controller;

import com.sanjana.issuetracker.exception.DeveloperNotFoundException;
import com.sanjana.issuetracker.exception.InvalidUpdateException;
import com.sanjana.issuetracker.exception.StoryNotFoundException;
import com.sanjana.issuetracker.model.Story;
import com.sanjana.issuetracker.service.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class StoryController {

    @Autowired
    private StoryService storyService;

    @PostMapping(value = "/stories")
    public ResponseEntity<String> createStory(@RequestBody final Story story){
        try {
            storyService.createStory(story);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DeveloperNotFoundException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/stories")
    public ResponseEntity<Set<Story>> getStories(){
        return new ResponseEntity<>(storyService.getStories(),HttpStatus.OK);
    }

    @PutMapping(value = "/stories")
    public ResponseEntity<String> updateStory(@RequestBody final Story story){
        try {
            storyService.updateStory(story);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (DeveloperNotFoundException | InvalidUpdateException ex){
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        } catch (StoryNotFoundException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

}
