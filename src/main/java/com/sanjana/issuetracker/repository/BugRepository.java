package com.sanjana.issuetracker.repository;

import com.sanjana.issuetracker.model.Bug;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository
public class BugRepository {

    private Set<Bug> bugs = new HashSet<>();

    public void createBug(Bug bug){
        bugs.add(bug);
    }

    public Set<Bug> getBugs(){
        return bugs;
    }
}
