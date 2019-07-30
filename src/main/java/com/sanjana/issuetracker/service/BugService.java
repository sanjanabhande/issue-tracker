package com.sanjana.issuetracker.service;

import com.sanjana.issuetracker.exception.BugNotFoundException;
import com.sanjana.issuetracker.exception.DeveloperNotFoundException;
import com.sanjana.issuetracker.exception.InvalidUpdateException;
import com.sanjana.issuetracker.model.Bug;
import com.sanjana.issuetracker.model.BugStatus;
import com.sanjana.issuetracker.repository.BugRepository;
import com.sanjana.issuetracker.repository.DeveloperRepository;
import com.sanjana.issuetracker.repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Service
public class BugService {

    @Autowired
    private BugRepository bugRepository;

    @Autowired
    private DeveloperRepository developerRepository;

    public void createBug(final Bug bug){
        bug.setStatus(BugStatus.NEW);
        bug.getIssue().setId(++IssueRepository.issueId);
        bug.getIssue().setCreationDate(new Date());
        if (bug.getIssue().getDeveloper()!=null && bug.getIssue().getDeveloper().getName()!=null && bug.getIssue().getDeveloper().getName()!=""){
            checkDeveloperAvailable(bug);
        }
        bugRepository.createBug(bug);
    }

    public Set<Bug> getBugs(){
        return bugRepository.getBugs();
    }

    public void updateBugs(final Bug bug){
        final Optional<Bug> updateBugById = bugRepository.getBugs()
                .stream()
                .filter(bugId -> bugId.getIssue().getId()==bug.getIssue().getId())
                .findFirst();
        checkBugPresent(updateBugById);
        updateIssueDetails(bug,updateBugById);
        updateBugDetails(bug,updateBugById);
    }

    private void checkBugPresent(final Optional<Bug> updateBugById){
        if (!updateBugById.isPresent()){
            throw new BugNotFoundException("Bug to be updated does not exist");
        }
    }

    private void updateIssueDetails(final Bug bug, final Optional<Bug> updateBugById){
        if (bug.getIssue().getDeveloper()!=null && bug.getIssue().getDeveloper().getName()!=null && bug.getIssue().getDeveloper().getName()!=""){
            checkDeveloperAvailable(bug);
            updateBugById.get().getIssue().setDeveloper(bug.getIssue().getDeveloper());
        }
        if (bug.getIssue().getTitle()!=null){
            updateBugById.get().getIssue().setTitle(bug.getIssue().getTitle());
        }
        if (bug.getIssue().getDescription()!=null){
            updateBugById.get().getIssue().setDescription(bug.getIssue().getDescription());
        }
        if (bug.getIssue().getCreationDate()!=null){
            throw new InvalidUpdateException("Creation date of the bug cannot be updated");
        }
    }

    private void checkDeveloperAvailable(final Bug bug){
        if (!developerRepository.getDevelopers().contains(bug.getIssue().getDeveloper())){
            throw new DeveloperNotFoundException("Developer not found to assign the bug");
        }
    }

    private void updateBugDetails(final Bug bug, final Optional<Bug> updateBugById){
        if (bug.getPriority()!=null){
            updateBugById.get().setPriority(bug.getPriority());
        }
        if (bug.getStatus()!=null){
            if (bug.getStatus().equals(BugStatus.VERIFIED)){
                updateBugById.get().setStatus(bug.getStatus());
            } else if (bug.getStatus().equals(BugStatus.RESOLVED)){
                updateBugById.get().setStatus(bug.getStatus());
            } else {
                updateBugById.get().setStatus(BugStatus.NEW);
            }
        }
    }

}
