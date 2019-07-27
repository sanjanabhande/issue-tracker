package com.sanjana.issuetracker.controller;

import com.sanjana.issuetracker.exception.DeveloperNotFoundException;
import com.sanjana.issuetracker.exception.DuplicateDeveloperException;
import com.sanjana.issuetracker.model.Developer;
import com.sanjana.issuetracker.service.DeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class DeveloperController {

    @Autowired
    private DeveloperService developerService;

    @PostMapping(value = "/developers")
    public ResponseEntity<String> createDeveloper(@RequestBody final Developer developer){
        try {
            developerService.createDeveloper(developer);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DuplicateDeveloperException ex){
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.CONFLICT);
        }
    }

    @GetMapping(value = "/developers")
    public ResponseEntity<Set<Developer>> getDevelopers(){
        return new ResponseEntity<>(developerService.getDevelopers(),HttpStatus.OK);
    }

    @DeleteMapping(value = "/developers")
    public ResponseEntity<String> deleteDeveloper(@RequestBody final Developer developer){
        try {
            developerService.deleteDeveloper(developer);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (DeveloperNotFoundException ex){
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

}
