package com.sanjana.issuetracker.model;

import lombok.Value;

import java.util.List;

@Value
public class SprintResponse {
    List<Sprint> sprints;
}
