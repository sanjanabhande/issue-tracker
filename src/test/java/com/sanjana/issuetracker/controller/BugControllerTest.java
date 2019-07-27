package com.sanjana.issuetracker.controller;

import com.sanjana.issuetracker.service.BugService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BugControllerTest {

    @Mock
    private BugService bugService;

    @InjectMocks
    private BugController bugController;

    @Test
    public void createBug(){
        bugController.createBug(Mockito.any());
        Mockito.verify(bugService).createBug(Mockito.any());
    }

    @Test
    public void getBugsTest(){
        bugController.getBugs();
        Mockito.verify(bugService).getBugs();
    }

    @Test
    public void updateBugTest(){
        bugController.updateBugs(Mockito.any());
        Mockito.verify(bugService).updateBugs(Mockito.any());
    }

}
