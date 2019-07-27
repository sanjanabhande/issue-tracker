package com.sanjana.issuetracker.repository;

import com.sanjana.issuetracker.model.Developer;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository
public class DeveloperRepository {

    private final Set<Developer> developers = new HashSet<>();

    public void createDeveloper(Developer developer){
        developers.add(developer);
    }

    public Set<Developer> getDevelopers() {
        return developers;
    }

    public boolean deleteDeveloper(Developer developer){
        return developers.remove(developer);
    }
}
