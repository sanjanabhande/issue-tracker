package com.sanjana.issuetracker.model;

import lombok.Data;

@Data
public class Bug{

    Issue issue;
    BugPriority priority;
    BugStatus status;

}
