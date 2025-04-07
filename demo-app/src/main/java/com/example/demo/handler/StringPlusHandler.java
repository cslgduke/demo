package com.example.demo.handler;

import com.example.demo.annotation.PreHandler;
import com.example.demo.aspect.OdataHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author i565244
 */
@PreHandler
@Component
@Slf4j
public class StringPlusHandler implements OdataHandler{

    @Override
    public void handle() {
        log.info("StringPlusHandler");
    }
}
