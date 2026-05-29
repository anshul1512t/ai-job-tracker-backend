package com.ai.job_tracker.exception;

import org.springframework.http.HttpStatus;

public class ErrorResponse {

    private String message;
    private HttpStatus status;
    private long timestamp;

    public ErrorResponse(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
        this.timestamp = System.currentTimeMillis();
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public long getTimestamp() {
        return timestamp;
    }
}