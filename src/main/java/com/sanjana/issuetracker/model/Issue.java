package com.sanjana.issuetracker.model;

import lombok.Data;

import java.util.Date;

@Data
public class Issue {

    Integer id;
    String title;
    String description;
    Date creationDate;
    Developer developer;

}
