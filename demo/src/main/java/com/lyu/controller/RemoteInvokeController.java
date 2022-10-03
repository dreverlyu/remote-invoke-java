package com.lyu.controller;

import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
public class RemoteInvokeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RemoteInvokeController.class);
    private final static String SUCCESS_RESULT = "success";

    private static RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(60000).setConnectTimeout(60000).build();
    private static final String chaset = "UTF-8";    // 默认编码


    @GetMapping("/restTemplate")
    public String testRest() {
        return SUCCESS_RESULT;
    }

    @GetMapping("/httpUtils")
    public String testUtils() {

        return SUCCESS_RESULT;
    }

    @GetMapping("/client")
    public String testOkHttp() {
        String url = "https://api.oceandrivers.com:443/v1.0/getWebCams/";
        HttpGet httpGet = new HttpGet(url);
        String result = "";
        try (CloseableHttpClient httpclient = HttpClients.createDefault();// 创建Httpclient对象
             CloseableHttpResponse response = httpclient.execute(httpGet);// 执行请求
        ) {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                result = EntityUtils.toString(response.getEntity(), chaset);
                LOGGER.info("HttpGet方式请求成功！返回结果：{}", result);
            } else {
                LOGGER.info("HttpGet方式请求失败！状态码:" + statusCode);
            }
        } catch (ClientProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SUCCESS_RESULT;
    }
}
