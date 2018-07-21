package org.example;

import java.util.concurrent.Executors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ProcessEJB {

    @PersistenceContext
    private EntityManager entityManager;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void execute(final int id) {

        Executors.newSingleThreadExecutor().execute(new Runnable() {
            public void run() {
                Test test = HibernateUtil.lockTable(entityManager, id);
                System.out.println("test: " + test);
                if (test == null) {
                    throw new IllegalStateException();
                }
            }
        });
    }
}
