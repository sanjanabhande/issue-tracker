package com.sanjana.issuetracker.controller;

import com.sanjana.issuetracker.exception.BugNotFoundException;
import com.sanjana.issuetracker.exception.DeveloperNotFoundException;
import com.sanjana.issuetracker.exception.InvalidUpdateException;
import com.sanjana.issuetracker.model.Bug;
import com.sanjana.issuetracker.service.BugService;
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
public class BugController {

    @Autowired
    private BugService bugService;

    @PostMapping(value = "/bugs")
    public ResponseEntity<String> createBug(@RequestBody Bug bug){
        bugService.createBug(bug);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/bugs")
    public ResponseEntity<Set<Bug>> getBugs(){
        return new ResponseEntity<>(bugService.getBugs(),HttpStatus.OK);
    }

    @PutMapping(value = "/bugs")
    public ResponseEntity<String> updateBugs(@RequestBody Bug bug){
        try {
            bugService.updateBugs(bug);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (DeveloperNotFoundException | InvalidUpdateException ex){
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        } catch (BugNotFoundException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

}
