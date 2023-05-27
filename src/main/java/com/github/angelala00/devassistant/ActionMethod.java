package com.github.angelala00.devassistant;

import com.github.angelala00.devassistant.openai.MessageItem;
import com.github.angelala00.devassistant.openai.OpenaiTool;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

public class ActionMethod {
    private OpenaiTool openaiTool = new OpenaiTool();

    public void sendMessage(JTextField inputTextField, JTextArea chatContentArea,
                            JButton sendButton, ExecutorService executorService,
                            JList<String> sessionList, Map<String, ArrayList<MessageItem>> sessionChatMap) {
        //处理？
        if (sessionList == null) {
            System.out.println("sessionList is null");
            return;
        }
        String selectedSession = sessionList.getSelectedValue();

        if (selectedSession == null) {
            System.out.println("selectedSession is null");
            return;
//            selectedSession = new String();
//            sessionChatMap.put(selectedSession, chatContentArea.getText());
        }

        String inputText = inputTextField.getText().trim();
        if (inputText.isEmpty()) {
            System.out.println("inputText is empty");
            return;
        }

        MessageItem item = new MessageItem("user", inputText);
        updateSessionWithMessageItem(selectedSession, chatContentArea, item, sessionChatMap);
        // 清除输入区域的文本
        inputTextField.setText("");
        // 禁用发送按钮和输入框的回车功能
        sendButton.setEnabled(false);
        // 使用 CompletableFuture 异步调用接口
        CompletableFuture.supplyAsync(() -> openaiTool.chatCompletions(sessionChatMap.get(selectedSession),chatContentArea), executorService).thenAccept(response -> {
            // 将接收到的响应添加到聊天内容区域
            SwingUtilities.invokeLater(() -> {
                MessageItem item2 = new MessageItem("assistant", response);
                sessionChatMap.get(selectedSession).add(item2);

//                updateSessionWithMessageItem(selectedSession, chatContentArea, item2, sessionChatMap);
                // 启用发送按钮和输入框的回车功能
                sendButton.setEnabled(true);
            });
        });
    }

    private void updateSessionWithMessageItem(String selectedSession, JTextArea chatContentArea,
                                              MessageItem messageItem, Map<String, ArrayList<MessageItem>> sessionChatMap) {
        // 更新会话聊天记录
        sessionChatMap.get(selectedSession).add(messageItem);
        if (chatContentArea.getText().startsWith(selectedSession + ":")) {
            // 将输入的文本添加到聊天内容区域
            chatContentArea.append(renderSingleMessageItem(messageItem));
        }
    }

    public String renderSingleMessageItem(MessageItem messageItem) {
        return "\n" + messageItem.getRole() + ":\n" + messageItem.getContent();
    }
}



