package com.sanjana.issuetracker.model;

import lombok.Data;

import java.util.Date;
import java.util.Objects;

@Data
public class Issue {

    Integer id;
    String title;
    String description;
    Date creationDate;
    Developer developer;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Issue issue = (Issue) o;
        return Objects.equals(id, issue.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
