package de.htw.fb4.imi.jumpup.cdi;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class Service {

    @PersistenceContext
    private EntityManager em;

}
