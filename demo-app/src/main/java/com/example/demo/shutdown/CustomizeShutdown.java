package com.example.demo.shutdown;

import com.example.demo.rest.CommonController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.concurrent.TimeUnit;

/**
 * @author i565244
 */
@Component
@Slf4j
public class CustomizeShutdown implements DisposableBean {
    @Override
    public void destroy() throws Exception {
        log.info("CustomizeShutdown destroy triggered by DisposableBean interface");
        shutdownTP();
    }

    @PreDestroy
    public void onShutdown() {
        log.info("CustomizeShutdown onShutdown triggered by PreDestroy annotation");
    }

    private void shutdownTP(){
        CommonController.commonExecutor.shutdown();
        try {
            if (!CommonController.commonExecutor.awaitTermination(60, TimeUnit.SECONDS)) {
                CommonController.commonExecutor.shutdownNow();
            }
        } catch (InterruptedException ex) {
            CommonController.commonExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }
        log.info("ThreadPoolExecutor shutdown");
    }
}
