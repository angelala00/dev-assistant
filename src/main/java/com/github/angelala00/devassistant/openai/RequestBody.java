package com.github.angelala00.devassistant.openai;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class RequestBody {
    @JsonProperty("model")
    private String model;

    @JsonProperty("messages")
    private List<MessageItem> messages;

    @JsonProperty("stream")
    private boolean stream;

    public RequestBody(String model, List<MessageItem> messages, boolean stream) {
        this.model = model;
        this.messages = messages;
        this.stream = stream;
    }
}
