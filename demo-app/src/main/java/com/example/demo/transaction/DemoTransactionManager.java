package com.example.demo.transaction;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionSynchronizationManager;


@Slf4j
public class DemoTransactionManager implements PlatformTransactionManager {

    private DemoTransaction transaction;

    public DemoTransactionManager(DemoTransaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public TransactionStatus getTransaction(TransactionDefinition definition) throws TransactionException {
        log.debug("Start transaction...");
        TransactionSynchronizationManager.setActualTransactionActive(true);
        TransactionSynchronizationManager.initSynchronization();
        return createTransactionStatus();
    }

    private TransactionStatus createTransactionStatus() {
        return new TransactionStatus() {

            @Override
            public Object createSavepoint() throws TransactionException {
                return null;
            }

            @Override
            public void releaseSavepoint(Object arg0) throws TransactionException {
            }

            @Override
            public void rollbackToSavepoint(Object arg0) throws TransactionException {
            }

            @Override
            public void flush() {
            }

            @Override
            public boolean hasSavepoint() {
                return false;
            }

            @Override
            public boolean isCompleted() {
                return false;
            }

            @Override
            public boolean isNewTransaction() {
                return false;
            }

            @Override
            public boolean isRollbackOnly() {
                return false;
            }

            @Override
            public void setRollbackOnly() {
            }
        };
    }

    @Override
    public void commit(TransactionStatus status) throws TransactionException {
        log.debug("Commit transaction start...");
        try {
            getTransaction().commit();
            TransactionSynchronizationManager.clear();
            TransactionSynchronizationManager.setActualTransactionActive(false);
            log.debug("Commit transaction end.");
        } catch (Exception e) {
            log.error("Transaction rollback due to exception.", e);
            rollback(status);
        }
    }

    @Override
    public void rollback(TransactionStatus status) throws TransactionException {
        log.debug("Rollback transaction start...");
        try {
            getTransaction().rollback();
        } finally {
            TransactionSynchronizationManager.clear();
            TransactionSynchronizationManager.setActualTransactionActive(false);
        }

        log.debug("Rollback transaction end.");
    }

    private DemoTransaction getTransaction() {
        return transaction;
    }
}
