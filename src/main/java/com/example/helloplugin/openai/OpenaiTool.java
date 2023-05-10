package com.example.helloplugin.openai;

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
        System.setProperty("http.proxyHost", "127.0.0.1");
        System.setProperty("http.proxyPort", "1087");
        System.setProperty("https.proxyHost", "127.0.0.1");
        System.setProperty("https.proxyPort", "1087");

        int connectionTimeout = 15000; // 连接超时时间（毫秒）
        int requestTimeout = 15000;    // 请求超时时间（毫秒）
        int socketTimeout = 15000;     // 套接字超时时间（毫秒）

        String proxyHost = "127.0.0.1";
        int proxyPort = 1087;
        HttpHost proxy = new HttpHost(proxyHost, proxyPort);

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(connectionTimeout)
                .setConnectionRequestTimeout(requestTimeout)
                .setSocketTimeout(socketTimeout)
                .setProxy(proxy) // 设置代理服务器
                .build();

        String url = "https://api.openai.com/v1/chat/completions";

        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(requestConfig)
                .build();
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Authorization", "Bearer ***");
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
