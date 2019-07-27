package com.sanjana.issuetracker.model;

import lombok.Value;

import java.util.List;

@Value
public class Sprint {

    int id;
    String name;
    List<Developer> developers;
    List<Story> stories;
}
