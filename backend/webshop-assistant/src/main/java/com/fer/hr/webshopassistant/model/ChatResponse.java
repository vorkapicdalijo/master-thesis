package com.fer.hr.webshopassistant.model;


public class ChatResponse {

    private String message;

    public ChatResponse(String message) {
        this.message = message;
    }

    public ChatResponse() {

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
