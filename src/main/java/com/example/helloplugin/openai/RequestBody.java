package com.example.helloplugin.openai;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class RequestBody {
    @JsonProperty("model")
    private String model;

    @JsonProperty("messages")
    private List<MessageItem> messages;

    public RequestBody(String model, List<MessageItem> messages) {
        this.model = model;
        this.messages = messages;
    }
}
