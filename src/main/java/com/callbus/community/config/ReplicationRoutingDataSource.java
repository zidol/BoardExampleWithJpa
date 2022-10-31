package com.callbus.community.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
public class ReplicationRoutingDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        if (TransactionSynchronizationManager.isCurrentTransactionReadOnly()) {
            log.info("slave db..");
        } else {
            log.info("master db..");
        }
        return TransactionSynchronizationManager.isCurrentTransactionReadOnly()
                ? "slave"
                : "master";
    }
}
