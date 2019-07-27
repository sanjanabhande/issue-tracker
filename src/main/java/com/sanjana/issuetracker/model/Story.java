package com.sanjana.issuetracker.model;

import lombok.Data;

@Data
public class Story{

    Issue issue;
    Integer estimation;
    StoryStatus status;

}
