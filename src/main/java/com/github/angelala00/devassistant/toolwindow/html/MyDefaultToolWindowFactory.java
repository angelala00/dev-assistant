package com.github.angelala00.devassistant.toolwindow.html;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.ui.jcef.JBCefApp;
import com.intellij.ui.jcef.JBCefBrowser;
import com.intellij.ui.jcef.JBCefBrowserBase;
import com.intellij.ui.jcef.JBCefJSQuery;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.browser.CefMessageRouter;
import org.cef.callback.CefQueryCallback;
import org.cef.handler.CefLoadHandlerAdapter;
import org.cef.handler.CefMessageRouterHandlerAdapter;
import org.cef.network.CefRequest;
import org.jetbrains.annotations.NotNull;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class MyDefaultToolWindowFactory implements ToolWindowFactory {
    String testvar1;
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        if (JBCefApp.isSupported()) {
            System.out.println("JCEF is supported");
        } else {
            System.out.println("JCEF is not supported");
        }
        String html = "";
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("html/toolwindow.html");
        if (inputStream == null) {
            html = "File html/toolwindow.html was not found inside JAR.";
        } else {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            html = reader.lines().collect(Collectors.joining("\n"));
        }
        JBCefBrowser jbCefBrowser = new JBCefBrowser();
        jbCefBrowser.loadHTML(html);
        JComponent browserComponent = jbCefBrowser.getComponent();




//        JBCefJSQuery jsQuery = JBCefJSQuery.create(jbCefBrowser, (String query) -> {
//            // 解析请求
//            JsonObject jsonObject = new Gson().fromJson(query, JsonObject.class);
//            String command = jsonObject.get("command").getAsString();
//            String value = jsonObject.get("value").getAsString();
//
//            // 处理请求
//            if ("setTestVar1".equals(command)) {
//                testvar1 = value;
//                return "";  // 返回给JavaScript的结果
//            }
//            return null;
//        });





        // 创建一个新的 Content
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(browserComponent, "", false);
        // 将 Content 添加到 ToolWindow
        toolWindow.getContentManager().addContent(content);
    }

}
