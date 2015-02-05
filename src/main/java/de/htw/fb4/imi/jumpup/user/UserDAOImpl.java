/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.user;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import de.htw.fb4.imi.jumpup.settings.BeanNames;
import de.htw.fb4.imi.jumpup.settings.PersistenceSettings;
import de.htw.fb4.imi.jumpup.user.entities.User;

/**
 * <p>Data access object implementation to work with {@link User} entities.</p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 30.01.2015
 *
 */
@Stateless(name = BeanNames.USER_DAO)
public class UserDAOImpl implements UserDAO
{
    @PersistenceContext(unitName = PersistenceSettings.PERSISTENCE_UNIT)
    protected EntityManager em;


    @Override
    /*
     * (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.user.UserDAO#loadById(long)
     */
    public User loadById(long identity)
    {
        final Query query = this.em.createNamedQuery(User.NAME_QUERY_BY_ID,
                User.class);
        query.setParameter("identity", new Long(identity));
        return (User) query.getSingleResult();
    }

}
