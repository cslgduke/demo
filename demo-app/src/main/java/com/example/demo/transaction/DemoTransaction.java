package com.example.demo.transaction;

import lombok.extern.slf4j.Slf4j;

/**
 * @author i565244
 */
@Slf4j
public class DemoTransaction {


    public void begin() {
        log.debug("Transaction beginning...");
    }


    public void commit() {
        log.debug("Transaction committing...");
    }

    public void rollback() {
        try {
            log.debug("DB Transaction rolled back.");
        } catch (Exception e) {
            log.error("Failed rollback DB transaction", e);
        }
    }

    public Boolean getTransactionStatus() {
        return  true;
    }

}
