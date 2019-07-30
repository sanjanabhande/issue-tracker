package com.sanjana.issuetracker.service;

import com.sanjana.issuetracker.model.SprintResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SprintService {

    @Autowired
    private SprintEngine sprintEngine;

    public SprintResponse plan(){
        return new SprintResponse(sprintEngine.plan());
    }
}
