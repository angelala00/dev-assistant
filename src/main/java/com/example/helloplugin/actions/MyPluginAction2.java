package com.example.helloplugin.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;

public class MyPluginAction2 extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent e) {
        // 在这里实现你的操作功能
        Messages.showMessageDialog("Hello, world!", "My Plugin Action", Messages.getInformationIcon());
    }
}
