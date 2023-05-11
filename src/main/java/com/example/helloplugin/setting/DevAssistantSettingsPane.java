package com.example.helloplugin.setting;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class DevAssistantSettingsPane implements SearchableConfigurable {
    private JPanel panel;
    private JTextField serverUrlField;

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "DevAssistant";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        return panel;
    }

    @Override
    public boolean isModified() {
        DevAssistantSettings settings = DevAssistantSettings.getInstance();
        return !serverUrlField.getText().equals(settings.serverUrl);
    }

    @Override
    public void apply() throws ConfigurationException {
        DevAssistantSettings settings = DevAssistantSettings.getInstance();
        settings.serverUrl = serverUrlField.getText();
    }

    @Override
    public void reset() {
        DevAssistantSettings settings = DevAssistantSettings.getInstance();
        serverUrlField.setText(settings.serverUrl);
    }

    @Override
    public @NotNull @NonNls String getId() {
        return "chatBotSettings";
    }
}