package com.example.helloplugin.openai;

import com.example.helloplugin.setting.DevAssistantSettings;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.util.ArrayList;

public class OpenaiTool {

    public String chatCompletions(ArrayList<MessageItem> messageItems) {

        int connectionTimeout = 15000; // 连接超时时间（毫秒）
        int requestTimeout = 15000;    // 请求超时时间（毫秒）
        int socketTimeout = 15000;     // 套接字超时时间（毫秒）

        String proxyHost = DevAssistantSettings.getInstance().proxyHost;
        int proxyPort = DevAssistantSettings.getInstance().proxyPort;

        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom()
                .setConnectTimeout(connectionTimeout)
                .setConnectionRequestTimeout(requestTimeout)
                .setSocketTimeout(socketTimeout);
        // 如果 proxyHost 不为空且 proxyPort 大于 0，则设置代理服务器
        if (proxyHost != null && !proxyHost.isEmpty() && proxyPort > 0) {
            HttpHost proxy = new HttpHost(proxyHost, proxyPort);
            requestConfigBuilder.setProxy(proxy);
        }

        RequestConfig requestConfig = requestConfigBuilder.build();

        String url = DevAssistantSettings.getInstance().serverUrl + "/v1/chat/completions";
        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(requestConfig)
                .build();
        try {
            String apiKey = DevAssistantSettings.getInstance().personalApiKey;
            HttpPost httpPost = new HttpPost(url);
            if (apiKey != null && !apiKey.isEmpty()) {
                httpPost.setHeader("Authorization", "Bearer " + apiKey);
            }
            httpPost.setHeader("Content-Type", "application/json");

            // 创建 RequestBody 对象
            RequestBody requestBody = new RequestBody("gpt-3.5-turbo", messageItems);

            // 将 RequestBody 转换为 JSON 字符串
            ObjectMapper mapper = new ObjectMapper();
            String requestBodyString = mapper.writeValueAsString(requestBody);

            System.out.println("requestBody:" + requestBodyString);
            // 创建 StringEntity 对象
            StringEntity requestEntity = new StringEntity(requestBodyString, ContentType.APPLICATION_JSON);
            httpPost.setEntity(requestEntity);

            CloseableHttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(entity.getContent());
                // 在这里解析JSON响应内容
                System.out.println("Response all content: " + entity);

                JsonNode messageNode = rootNode.path("choices").get(0).path("message");
                String content = messageNode.path("content").asText();
                System.out.println("Response content: " + content);
                response.close();
                httpClient.close();
                return content;
            }
            response.close();
            httpClient.close();
            return "{}";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "{}";
    }
}
