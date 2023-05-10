package com.example.helloplugin.openai;

public class MessageItem {
    public MessageItem(String role, String content) {
        this.role = role;
        this.content = content;
    }

    String role;
    String content;

    public String getRole() {
        return role;
    }

    public String getContent() {
        return content;
    }
}
