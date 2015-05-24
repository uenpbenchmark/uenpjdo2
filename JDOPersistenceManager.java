package com.uenpjdo1;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

public final class JDOPersistenceManager {
    private static final PersistenceManagerFactory pmfInstance =
        JDOHelper.getPersistenceManagerFactory("transactions-optional");

    private JDOPersistenceManager() {}

    public static PersistenceManagerFactory get() {
        return pmfInstance;
    }
}