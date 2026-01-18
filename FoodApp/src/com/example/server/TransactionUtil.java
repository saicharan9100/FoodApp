package com.example.server;

import java.util.function.Supplier;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

public class TransactionUtil {

    public static <T> T doInTransaction(Supplier<T> action) {
        PlatformTransactionManager tm = (PlatformTransactionManager) ApplicationContextListener.applicationContext.getBean("transactionManager");
        TransactionTemplate template = new TransactionTemplate(tm);
        return template.execute(status -> action.get());
    }

    public static void doInTransaction(Runnable action) {
        PlatformTransactionManager tm = (PlatformTransactionManager) ApplicationContextListener.applicationContext.getBean("transactionManager");
        TransactionTemplate template = new TransactionTemplate(tm);
        template.execute(status -> {
            action.run();
            return null;
        });
    }
}
