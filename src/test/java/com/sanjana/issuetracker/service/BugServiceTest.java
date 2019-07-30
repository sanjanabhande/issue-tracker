package com.sanjana.issuetracker.service;

import com.sanjana.issuetracker.exception.BugNotFoundException;
import com.sanjana.issuetracker.exception.DeveloperNotFoundException;
import com.sanjana.issuetracker.exception.InvalidUpdateException;
import com.sanjana.issuetracker.model.Bug;
import com.sanjana.issuetracker.model.BugPriority;
import com.sanjana.issuetracker.model.BugStatus;
import com.sanjana.issuetracker.model.Developer;
import com.sanjana.issuetracker.model.Issue;
import com.sanjana.issuetracker.repository.BugRepository;
import com.sanjana.issuetracker.repository.DeveloperRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
public class BugServiceTest {

    @Mock
    private BugRepository bugRepository;

    @Mock
    private DeveloperRepository developerRepository;

    @InjectMocks
    private BugService bugService;

    @Test
    public void createBugTest(){
        setUpBugData();
        bugService.createBug(bug);
        Mockito.verify(bugRepository).createBug(bug);
    }

    @Test
    public void updateExistingBugTest(){
        setUp();
        Issue issue = new Issue();
        issue.setId(0);
        bug.setIssue(issue);
        bug.setStatus(BugStatus.RESOLVED);
        bugService.updateBugs(bug);
        bugs.parallelStream();
        Assert.assertEquals(BugStatus.RESOLVED,
                bugs.stream()
                        .filter(bugItem -> bugItem.getIssue().getId().equals(bug.getIssue().getId()))
                        .findFirst()
                        .get().getStatus());
    }

    @Test(expected = BugNotFoundException.class)
    public void updateNonExistingBugTest(){
        setUp();
        Bug bug = new Bug();
        Issue issue = new Issue();
        issue.setId(10);
        bug.setIssue(issue);
        bug.setStatus(BugStatus.RESOLVED);
        bugService.updateBugs(bug);
    }

    @Test(expected = InvalidUpdateException.class)
    public void updateCreationDateBugTest(){
        setUp();
        Issue issue = new Issue();
        issue.setId(1);
        issue.setCreationDate(new Date());
        bug.setIssue(issue);
        bug.setStatus(BugStatus.RESOLVED);
        bugService.updateBugs(bug);
    }

    @Test(expected = DeveloperNotFoundException.class)
    public void updateInvalidDeveloperBugTest(){
        setUp();
        Developer developer = new Developer();
        developer.setName("Prashu");
        Issue issue = new Issue();
        issue.setId(0);
        issue.setDeveloper(developer);
        bug.setIssue(issue);
        bug.setStatus(BugStatus.RESOLVED);
        bugService.updateBugs(bug);
    }

    private Bug bug = new Bug();

    private Set<Bug> bugs = new HashSet<>();

    private Set<Developer> developers = new HashSet<>();

    private void setUp(){
        setUpBugsData();
        Mockito.when(bugRepository.getBugs()).thenReturn(bugs);
        Mockito.when(developerRepository.getDevelopers()).thenReturn(developers);
    }

    private void setUpBugData(){
        bug.setIssue(setUpIssueDetails(0));
        bug.setPriority(setPriorityDetails(0));
        bug.setStatus(setStatusDetails(0));
    }

    private void setUpBugsData(){
        for (int i = 0; i < 6 ; i++) {
            bug = new Bug();
            bug.setIssue(setUpIssueDetails(i));
            bug.setPriority(setPriorityDetails(i));
            bug.setStatus(setStatusDetails(i));
            bugs.add(bug);
        }
    }

    private Issue setUpIssueDetails(int i){
        Issue issueDetails = new Issue();
        Developer developer = new Developer();
        developer.setName("Sanju"+i);
        developers.add(developer);
        issueDetails.setId(i);
        issueDetails.setTitle("Test Title"+i);
        issueDetails.setDescription("Test Description"+i);
        issueDetails.setDeveloper(developer);
        return issueDetails;
    }

    private BugPriority setPriorityDetails(int i){
        if (i == 0 || i == 2){
            return BugPriority.CRITICAL;
        } else if (i == 1 || i == 3){
            return BugPriority.MAJOR;
        } else {
            return BugPriority.MINOR;
        }
    }

    private BugStatus setStatusDetails(int i){
        if (i == 0 || i == 2){
            return BugStatus.NEW;
        } else if (i == 1 || i == 3){
            return BugStatus.VERIFIED;
        } else {
            return BugStatus.RESOLVED;
        }
    }

}
