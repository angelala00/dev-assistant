package com.example.helloplugin.setting;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

public class DevAssistantSettingsPane implements SearchableConfigurable {
    private JPanel panel;
    private JTextField serverUrlField;
    private JTextField personalApiKeyField;
    private JTextField proxyHostField;
    private JTextField proxyPortField;
    private JTextField modelField;

    public DevAssistantSettingsPane() {
        panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        serverUrlField = new JTextField(20);
        personalApiKeyField = new JTextField(20);
        proxyHostField = new JTextField(20);
        proxyPortField = new JTextField(20);
        modelField = new JTextField(20);

        c.anchor = GridBagConstraints.WEST;
        c.gridx = 0;
        c.gridy = 0;
        panel.add(new JLabel("Server URL:"), c);

        c.gridx = 1;
        panel.add(serverUrlField, c);

        c.gridx = 0;
        c.gridy = 1;
        panel.add(new JLabel("Personal Api Key:"), c);

        c.gridx = 1;
        panel.add(personalApiKeyField, c);

        c.gridx = 0;
        c.gridy = 2;
        panel.add(new JLabel("Proxy Host:"), c);

        c.gridx = 1;
        panel.add(proxyHostField, c);

        c.gridx = 0;
        c.gridy = 3;
        panel.add(new JLabel("Proxy Port:"), c);

        c.gridx = 1;
        panel.add(proxyPortField, c);

        c.gridx = 0;
        c.gridy = 4;
        panel.add(new JLabel("Model:"), c);

        c.gridx = 1;
        panel.add(modelField, c);
    }

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
//        if (settings == null) {
//            System.out.println("settings is null");
//        }
//        if (serverUrlField == null) {
//            System.out.println("serverUrlField is null");
//        }
//        if (serverUrlField.getText() == null) {
//            System.out.println("serverUrlField.getText() is null");
//        }
        return !(serverUrlField.getText().equals(settings.serverUrl)&&
                personalApiKeyField.getText().equals(settings.personalApiKey)&&
                proxyHostField.getText().equals(settings.proxyHost)&&
                proxyPortField.getText().equals(Integer.toString(settings.proxyPort))&&
                modelField.getText().equals(settings.model)
        );
    }

    @Override
    public void apply() throws ConfigurationException {
        DevAssistantSettings settings = DevAssistantSettings.getInstance();
        settings.serverUrl = serverUrlField.getText();
        settings.personalApiKey = personalApiKeyField.getText();
        settings.proxyHost = proxyHostField.getText();
        settings.proxyPort = Integer.parseInt(proxyPortField.getText());
        settings.model = modelField.getText();
    }

    @Override
    public void reset() {
        DevAssistantSettings settings = DevAssistantSettings.getInstance();
        serverUrlField.setText(settings.serverUrl);
        personalApiKeyField.setText(settings.personalApiKey);
        proxyHostField.setText(settings.proxyHost);
        proxyPortField.setText(Integer.toString(settings.proxyPort));
        modelField.setText(settings.model);
    }

    @Override
    public @NotNull @NonNls String getId() {
        return "chatBotSettings";
    }
}