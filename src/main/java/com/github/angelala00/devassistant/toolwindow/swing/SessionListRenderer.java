package com.github.angelala00.devassistant.toolwindow.swing;

import com.github.angelala00.devassistant.openai.MessageItem;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class SessionListRenderer extends JPanel implements ListCellRenderer<String> {
    private JLabel sessionLabel;
    private JButton deleteButton;
    private final Map<String, ArrayList<MessageItem>> sessionChatMap;

    public SessionListRenderer(JList<String> list, Map<String, ArrayList<MessageItem>> sessionChatMap) {
        this.sessionChatMap = sessionChatMap;

        setLayout(new GridBagLayout());
        setOpaque(true);

        sessionLabel = new JLabel();
        deleteButton = new JButton("X");
        int buttonSize = 16;
        deleteButton.setPreferredSize(new Dimension(buttonSize, buttonSize));
        deleteButton.setContentAreaFilled(false);
        deleteButton.setFocusPainted(false);
        deleteButton.setOpaque(false);

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        add(sessionLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.weightx = 0;
        gridBagConstraints.fill = GridBagConstraints.NONE;
        add(deleteButton, gridBagConstraints);

        deleteButton.addActionListener(e -> {
            int selectedIndex = list.getSelectedIndex();
            if (selectedIndex != -1) {
                DefaultListModel<String> model = (DefaultListModel<String>) list.getModel();
                model.remove(selectedIndex);
                sessionChatMap.remove(sessionLabel.getText());
            }
        });
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends String> list, String value, int index, boolean isSelected, boolean cellHasFocus) {
        sessionLabel.setText(value);

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            sessionLabel.setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            sessionLabel.setForeground(list.getForeground());
        }
        return this;
    }
}
