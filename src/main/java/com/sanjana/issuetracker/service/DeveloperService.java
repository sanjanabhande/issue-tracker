package com.sanjana.issuetracker.service;

import com.sanjana.issuetracker.exception.DeveloperNotFoundException;
import com.sanjana.issuetracker.exception.DuplicateDeveloperException;
import com.sanjana.issuetracker.model.Developer;
import com.sanjana.issuetracker.repository.DeveloperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class DeveloperService {

    @Autowired
    private DeveloperRepository developerRepository;

    public void createDeveloper(final Developer developer){
        if (developerRepository.getDevelopers().contains(developer)){
            throw new DuplicateDeveloperException("Developer to be created already exists");
        }
        developerRepository.createDeveloper(developer);
    }

    public Set<Developer> getDevelopers(){
        return developerRepository.getDevelopers();
    }

    public void deleteDeveloper(Developer developer){
        if (!developerRepository.deleteDeveloper(developer)){
            throw new DeveloperNotFoundException("Developer to be deleted not found");
        }
    }

}
