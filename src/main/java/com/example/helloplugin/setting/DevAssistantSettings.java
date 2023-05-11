package com.example.helloplugin.setting;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(
        name = "DevAssistantSettings",
        storages = {@Storage("devAssistantSettings.xml")}
)
public class DevAssistantSettings implements PersistentStateComponent<DevAssistantSettings> {
    public String serverUrl;

    public static DevAssistantSettings getInstance() {
        Project defaultProject = ProjectManager.getInstance().getDefaultProject();
        return ServiceManager.getService(defaultProject, DevAssistantSettings.class);
    }
    @Nullable
    @Override
    public DevAssistantSettings getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull DevAssistantSettings state) {
        this.serverUrl = state.serverUrl;
    }
}
