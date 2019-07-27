package com.sanjana.issuetracker.service;

import com.sanjana.issuetracker.exception.DeveloperNotFoundException;
import com.sanjana.issuetracker.exception.DuplicateDeveloperException;
import com.sanjana.issuetracker.model.Developer;
import com.sanjana.issuetracker.repository.DeveloperRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
public class DeveloperServiceTest {

    @Mock
    private DeveloperRepository developerRepository;

    @InjectMocks
    private DeveloperService developerService;

    @Test(expected = DuplicateDeveloperException.class)
    public void createDuplicateDeveloperTest(){
        //given
        Developer developer = new Developer();
        developer.setName("Sanju");
        //when
        Developer duplicateDeveloper = new Developer();
        duplicateDeveloper.setName("Sanju");
        Set<Developer> developers = new HashSet<>();
        developers.add(duplicateDeveloper);
        Mockito.when(developerRepository.getDevelopers()).thenReturn(developers);
        developerService.createDeveloper(developer);
    }

    @Test
    public void createNewDeveloperTest(){
        //given
        Developer developer = new Developer();
        developer.setName("Sanju");
        //when
        Developer duplicateDeveloper = new Developer();
        duplicateDeveloper.setName("Prashu");
        Set<Developer> developers = new HashSet<>();
        developers.add(duplicateDeveloper);
        Mockito.when(developerRepository.getDevelopers()).thenReturn(developers);
        developerService.createDeveloper(developer);
        //then
        Mockito.verify(developerRepository).createDeveloper(developer);
    }

    @Test
    public void getDeveloperTest(){
        developerService.getDevelopers();
        Mockito.verify(developerRepository).getDevelopers();
    }

    @Test
    public void deleteDeveloperTest(){
        //given
        Developer developer = new Developer();
        developer.setName("Sanju");
        //when
        Mockito.when(developerRepository.deleteDeveloper(developer)).thenReturn(true);
        developerService.deleteDeveloper(developer);
        Mockito.verify(developerRepository).deleteDeveloper(Mockito.any());
    }

    @Test(expected = DeveloperNotFoundException.class)
    public void deleteDeveloperNotFoundTest(){
        //given
        Developer developer = new Developer();
        developer.setName("Sanju");
        //when
        Mockito.when(developerRepository.deleteDeveloper(developer)).thenReturn(false);
        developerService.deleteDeveloper(developer);
        Mockito.verify(developerRepository).deleteDeveloper(Mockito.any());
    }

}
