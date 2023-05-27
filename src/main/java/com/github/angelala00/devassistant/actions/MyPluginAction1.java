package com.github.angelala00.devassistant.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;

public class MyPluginAction1 extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent e) {
        // 获取当前项目
        Project project = e.getProject();
        // 获取 ToolWindowManager 实例
        ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(project);
        // 获取你的 ToolWindow
        ToolWindow toolWindow = toolWindowManager.getToolWindow("DevAssistant");
        // 打开 ToolWindow
        toolWindow.show();
        // 在此处执行其他操作
    }
}
