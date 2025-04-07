package com.example.demo.config;

import io.fabric8.kubernetes.client.AutoAdaptableKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author i565244
 */
@Configuration
public class KubernetesConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public KubernetesClient kubernetesClient() {
        KubernetesClient k8sClient = new AutoAdaptableKubernetesClient();
        k8sClient.getConfiguration().setWebsocketPingInterval(6 * 1000L);
        k8sClient.getConfiguration().setWebsocketTimeout(30 * 1000);
        k8sClient.getConfiguration().setConnectionTimeout(30 * 1000);
        k8sClient.getConfiguration().setRequestTimeout(30 * 1000);
        k8sClient.getConfiguration().setWatchReconnectInterval(500);
        return k8sClient;
    }

}
