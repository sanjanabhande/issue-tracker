package com.sanjana.issuetracker.model;

import lombok.Data;

import java.util.Objects;

@Data
public class Story implements Comparable<Story>{

    Issue issue;
    Integer estimation;
    StoryStatus status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Story story = (Story) o;
        return Objects.equals(issue.getId(), story.issue.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(issue.getId());
    }

    @Override
    public int compareTo(Story o) {
        return this.estimation.compareTo(o.estimation);
    }
}
