package com.example.demo.http;

import lombok.Generated;
import lombok.experimental.UtilityClass;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.security.NoSuchAlgorithmException;

/**
 * @author i540284
 */
@UtilityClass
@Generated
public class HttpConnectionManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpConnectionManager.class);

    private static final RequestConfig REQ_CONFIG =
            RequestConfig.custom()
                    .setConnectionRequestTimeout(5000)
                    .setConnectTimeout(6000)
                    .setSocketTimeout(5000)
                    .setExpectContinueEnabled(false)
                    .build();

    private static PoolingHttpClientConnectionManager cm = null;

    static {
        init();
    }

    private static void init() {
        LayeredConnectionSocketFactory layeredConnectionSocketFactory = null;
        try {
            layeredConnectionSocketFactory = new SSLConnectionSocketFactory(SSLContext.getDefault());
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("HttpConnectionManager init error", e);
        }

        if (layeredConnectionSocketFactory == null) {
            throw new IllegalArgumentException();
        }
        Registry<ConnectionSocketFactory> socketFactoryRegistry =
                RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("https", layeredConnectionSocketFactory)
                        .register("http", new PlainConnectionSocketFactory())
                        .build();
        cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        cm.setMaxTotal(200);
        cm.setDefaultMaxPerRoute(20);
    }

    public static CloseableHttpClient getHttpClient(CookieStore cookieStore) {
        return HttpClients.custom()
                .setConnectionManager(cm)
                .setDefaultCookieStore(cookieStore)
                .setDefaultRequestConfig(REQ_CONFIG)
                .build();
    }

    public static CloseableHttpClient getHttpClient() {
        return HttpClients.custom().setConnectionManager(cm).setDefaultRequestConfig(REQ_CONFIG).build();
    }
}
