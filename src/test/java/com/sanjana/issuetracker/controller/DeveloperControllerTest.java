package com.sanjana.issuetracker.controller;

import com.sanjana.issuetracker.service.DeveloperService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DeveloperControllerTest {

    @Mock
    private DeveloperService developerService;

    @InjectMocks
    private DeveloperController developerController;

    @Test
    public void createDeveloperTest(){
        developerController.createDeveloper(Mockito.any());
        Mockito.verify(developerService).createDeveloper(Mockito.any());
    }

    @Test
    public void getDeveloperTest(){
        developerController.getDevelopers();
        Mockito.verify(developerService).getDevelopers();
    }

    @Test
    public void deleteDeveloperTest(){
        developerController.deleteDeveloper(Mockito.any());
        Mockito.verify(developerService).deleteDeveloper(Mockito.any());
    }
}
