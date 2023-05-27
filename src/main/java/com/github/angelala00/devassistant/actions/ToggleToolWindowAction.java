package com.github.angelala00.devassistant.actions;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import org.jetbrains.annotations.NotNull;

public class ToggleToolWindowAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        // 获取当前项目
        Project project = e.getProject();
        if (project == null) {
            return;
        }

        // 获取ToolWindow
        ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(project);
        ToolWindow toolWindow = toolWindowManager.getToolWindow("DevAssistant");

        if (toolWindow == null) {
            return;
        }

        // 切换ToolWindow的显示和隐藏
        if (toolWindow.isVisible()) {
            toolWindow.hide(null);
        } else {
            toolWindow.show(null);
        }
    }
}
