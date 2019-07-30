package com.sanjana.issuetracker.model;

import lombok.Value;

import java.util.List;

@Value
public class Sprint {
    String name;
    List<Story> stories;
}
