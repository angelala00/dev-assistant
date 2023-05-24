package com.example.helloplugin.setting;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(name = "DevAssistantSettings", storages = {@Storage("devAssistantSettings.xml")})
public class DevAssistantSettings implements PersistentStateComponent<DevAssistantSettings> {
    public String serverUrl = "https://api.openai.com";
    public String personalApiKey = "";
    public String proxyHost = "";
    public int proxyPort = 0;
    public String model = "gpt-3.5-turbo";

    public static DevAssistantSettings getInstance() {
        return ApplicationManager.getApplication().getService(DevAssistantSettings.class);
    }

    @Nullable
    @Override
    public DevAssistantSettings getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull DevAssistantSettings state) {
        this.serverUrl = state.serverUrl;
        this.personalApiKey = state.personalApiKey;
        this.proxyHost = state.proxyHost;
        this.proxyPort = state.proxyPort;
        this.model = state.model;
    }
}
