package com.sanjana.issuetracker.exception;

public class BugNotFoundException extends RuntimeException {
    public BugNotFoundException(String message) {
        super(message);
    }
}
