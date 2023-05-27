package com.github.angelala00.devassistant.setting;

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
    public int connectionTimeout = 15000; // 连接超时时间（毫秒）
    public int requestTimeout = 15000;    // 请求超时时间（毫秒）
    public int socketTimeout = 15000;     // 套接字超时时间（毫秒）

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
        this.connectionTimeout = state.connectionTimeout;
        this.requestTimeout = state.requestTimeout;
        this.socketTimeout = state.socketTimeout;
    }
}
