package com.example.helloplugin;

import com.example.helloplugin.openai.MessageItem;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyDefaultToolWindowFactory implements ToolWindowFactory {
    private ExecutorService executorService = Executors.newFixedThreadPool(2);

    private JTextArea chatContentArea;
    private JTextField inputTextField;
    private JButton sendButton;
    private Map<String, ArrayList<MessageItem>> sessionChatMap;
    private ActionMethod actionMethod;

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {

        actionMethod = new ActionMethod();
        // 创建一个面板，并设置布局
        JPanel panel = new JPanel(new BorderLayout());
        sessionChatMap = new HashMap<>();
        // 创建左边的会话列表区域
        JPanel sessionListPanel = createSessionListPanel();
        panel.add(sessionListPanel, BorderLayout.WEST);

        // 创建右边的聊天窗口区域
        JPanel chatWindowPanel = createChatWindowPanel();
        panel.add(chatWindowPanel, BorderLayout.CENTER);

//        // 将面板添加到 ToolWindow
//        toolWindow.getComponent().getParent().add(panel);

        // 创建一个ContentFactory实例
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        // 创建一个Content，并将panel添加到其中，同时设置标题
        Content content = contentFactory.createContent(panel, "", false);
        // 将Content添加到ToolWindow的ContentManager
        toolWindow.getContentManager().addContent(content);

    }

    private JList<String> sessionList;

    private JPanel createSessionListPanel() {
        // 创建会话列表区域的面板，并设置布局
        JPanel sessionListPanel = new JPanel(new BorderLayout());

        // 添加新建会话按钮
        JButton newSessionButton = new JButton("+");
        sessionListPanel.add(newSessionButton, BorderLayout.NORTH);

        // 添加会话列表
        DefaultListModel<String> listModel = new DefaultListModel<>();
        sessionList = new JList<>(listModel);

        int preferredWidth = 20; // 修改这个值以调整会话列表的首选宽度
        sessionList.setPrototypeCellValue(String.format("%" + preferredWidth + "s", ""));

        sessionList.setCellRenderer(new SessionListRenderer(sessionList, sessionChatMap));
        sessionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        sessionListPanel.add(new JBScrollPane(sessionList), BorderLayout.CENTER);

        // 添加新建会话按钮点击事件
        newSessionButton.addActionListener(e -> {
            String chatName = "Chat" + (listModel.getSize() + 1);
            listModel.addElement(chatName);
            sessionList.setSelectedValue(chatName, true);
            sessionChatMap.put(chatName, new ArrayList<MessageItem>());
        });

        // 添加会话列表选中事件
        sessionList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedIndex = sessionList.getSelectedIndex();
                String content = "";
                if (selectedIndex >= 0) {
                    String selectedSession = sessionList.getModel().getElementAt(selectedIndex);
                    content = selectedSession + ":";
                    ArrayList<MessageItem> messageItems = sessionChatMap.get(selectedSession);
                    if (messageItems != null) {
                        for (MessageItem messageItem : messageItems) {
                            content += actionMethod.renderSingleMessageItem(messageItem);
                        }
                    }
                }
                chatContentArea.setText(content);
            }
        });
        return sessionListPanel;
    }

    private JPanel createChatWindowPanel() {
        // 创建聊天窗口区域的面板，并设置布局
        JPanel chatWindowPanel = new JPanel(new BorderLayout());

        // 聊天内容区域
        chatContentArea = new JTextArea();
        chatContentArea.setEditable(false);
        JScrollPane scrollPane = new JBScrollPane(chatContentArea);
        chatWindowPanel.add(scrollPane, BorderLayout.CENTER);

        // 输入区域
        inputTextField = new JTextField();
        chatWindowPanel.add(inputTextField, BorderLayout.SOUTH);

        // 发送按钮
        sendButton = new JButton("->");

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(inputTextField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        chatWindowPanel.add(inputPanel, BorderLayout.SOUTH);

        // 添加发送按钮点击事件
        sendButton.addActionListener(e -> actionMethod.sendMessage(inputTextField, chatContentArea, sendButton, executorService, sessionList, sessionChatMap));

        // 添加输入区域按键事件
        inputTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    actionMethod.sendMessage(inputTextField, chatContentArea, sendButton, executorService, sessionList, sessionChatMap);
                }
            }
        });
        return chatWindowPanel;
    }

}
