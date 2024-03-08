package com.example.demo.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.Builder;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.MessageFormat;
import java.util.concurrent.Executors;

/**
 * @author i565244
 */
//@SpringBootTest
@Slf4j
public class HttpTest {


    @Test
    public void test_httpPost() {
        var url = "http://localhost:18081/visit";
        url = MessageFormat.format(url,"10086");
        var httpPost = new HttpPost(url);
        var reqJson = JSON.toJSONString(Request.builder().apId("zhangsan").build());
        httpPost.setEntity(new StringEntity(reqJson, ContentType.APPLICATION_JSON));
        log.info("Http call endpoint: {} ,request body: {}.", url, reqJson);
        try (CloseableHttpResponse response = HttpConnectionManager.getHttpClient().execute(httpPost)) {
            var returnJson = EntityUtils.toString(response.getEntity());
            log.info("Http call response status: {}, response body: {}.", response.getStatusLine().getStatusCode(), returnJson);
        } catch (Exception e) {
            log.error("Call {} failed, json body: {}, error: {}", url, reqJson, e);
        }
    }

    @Test
    @SneakyThrows
    public void test_httpGet() {
        var url = "http://localhost:18081/visit";

        var param = "/10086";

        var httpGet = new HttpGet(url+param);
        var response = HttpConnectionManager.getHttpClient().execute(httpGet);
        var returnJson = EntityUtils.toString(response.getEntity());
        var result = JSON.parseObject(returnJson, new TypeReference<>(){});
        log.info("Http call response status: {}, response body: {}.", response.getStatusLine().getStatusCode(), returnJson);
    }

    @Data
    @Builder
    static  public class Request{
        private String apId;
    }

    @Data
    @Builder
    static public class Response<R>{
        private String code;
        private R data;
    }


    @Test
    @SneakyThrows
    public void test_2() {

        var executorService = Executors.newFixedThreadPool(50);
        for (int i = 0; i < 50; i++) {
            executorService.execute(() ->{
                while(true){
                    try{
                        var url = "http://localhost:30418/common/user";
                        var httpGet = new HttpGet(url);
                        var response = HttpConnectionManager.getHttpClient().execute(httpGet);
                        var returnJson = EntityUtils.toString(response.getEntity());
                        var result = JSON.parseObject(returnJson, new TypeReference<>(){});
                        log.info("Http call response status: {}, response body: {}.", response.getStatusLine().getStatusCode(), returnJson);

                    }catch (Exception e){
                        log.error("occur exception",e);
                    }
                }
            });
        }
        while(true){
            log.info(">>>>>>>>>>>>>>>>>>>>");
            Thread.sleep(1 * 1000);
        }
    }

}
