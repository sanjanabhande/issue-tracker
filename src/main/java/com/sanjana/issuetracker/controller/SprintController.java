package com.sanjana.issuetracker.controller;

import com.sanjana.issuetracker.model.SprintResponse;
import com.sanjana.issuetracker.service.SprintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SprintController {

    @Autowired
    private SprintService sprintService;

    @PostMapping("/plan")
    public ResponseEntity<SprintResponse> planSprint(){
        return ResponseEntity.ok(sprintService.plan());
    }
}
