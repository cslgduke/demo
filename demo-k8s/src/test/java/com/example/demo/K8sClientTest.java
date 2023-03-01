package com.example.demo;

import io.fabric8.kubernetes.client.KubernetesClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author i565244
 */
@SpringBootTest
@Slf4j
public class K8sClientTest {

    @Autowired
    private  KubernetesClient kubernetesClient;

    private static final String namespace = "tech-dev";

    private static final String CS_CONFIG_CONFIGMAP_NAME = "cs-config";

    @Test
    public void test_k8sClient(){
        var isReady = kubernetesClient.configMaps().inNamespace(namespace).withName(CS_CONFIG_CONFIGMAP_NAME).isReady();
        log.info("configmap info:{}",isReady);

        while(true){

        }
    }
}
