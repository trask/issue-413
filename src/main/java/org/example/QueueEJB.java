package org.example;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class QueueEJB {

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private ProcessEJB processEJB;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void execute() {
        Test test = new Test();
        entityManager.persist(test);

        test = HibernateUtil.lockTable(entityManager, test.getId());
        if (test == null) {
            throw new IllegalStateException();
        }
        
        processEJB.execute(test.getId());
    }

    @TransactionAttribute(TransactionAttributeType.NEVER)
    public List<Test> list() {
        return entityManager.createQuery("select t from Test t", Test.class)
                .getResultList();
    }
}
