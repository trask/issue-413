package org.example;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.hibernate.LockOptions;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

class HibernateUtil {

    static Test lockTable(EntityManager entityManager, Serializable id) {
        Session session = (Session) entityManager.getDelegate();
        @SuppressWarnings("deprecation")
        Test test = (Test) session.createCriteria(Test.class)
                .add(Restrictions.idEq(id))
                .uniqueResult();
        if (test != null) {
            session.refresh(test, LockOptions.UPGRADE);
        }
        return test;
    }
}
