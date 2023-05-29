package com.github.angelala00.devassistant.toolwindow.html;

public class JSBridge {
    private MyDefaultToolWindowFactory toolWindowFactory;

    public JSBridge(MyDefaultToolWindowFactory toolWindowFactory) {
        this.toolWindowFactory = toolWindowFactory;
    }

    public void setTestVar1(String value) {
        toolWindowFactory.testvar1 = value;
    }

    public String getTestVar1() {
        return toolWindowFactory.testvar1;
    }
}
