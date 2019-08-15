package com.solution.prode.payload;

import com.solution.prode.model.Status;

public class GeneralResponse {
    private Status status;
    private String message;

    public GeneralResponse(Status status, String message) {
        this.status = status;
        this.message = message;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status success) {
        this.status = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
