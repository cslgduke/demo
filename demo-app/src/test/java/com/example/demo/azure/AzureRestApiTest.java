package com.example.demo.azure;

import com.example.demo.http.HttpConnectionManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * @author i565244
 */

@ExtendWith(MockitoExtension.class)
@Slf4j
public class AzureRestApiTest {

    public static final String baseUrl = "https://storagefdipoc.blob.core.windows.net/";

    @Test
    public void test_blobContainerClient() {
        var containerName = "1003-data-cic-di-poc";
        var createUrl = baseUrl + containerName + "?restype=container";
        var httpPut = new HttpPut();
        try{
            CloseableHttpResponse response = HttpConnectionManager.getHttpClient().execute(httpPut);
            var returnJson = EntityUtils.toString(response.getEntity());
            log.info("Http call response status: {}, response body: {}.", response.getStatusLine().getStatusCode(), returnJson);
        } catch (Exception e) {
            log.error("Call {} failed, json body: {}, error: {}", createUrl, null, e);
        }
    }

}
