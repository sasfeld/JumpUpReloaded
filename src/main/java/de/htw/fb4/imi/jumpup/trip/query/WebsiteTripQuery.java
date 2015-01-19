/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.trip.query;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import de.htw.fb4.imi.jumpup.settings.BeanNames;
import de.htw.fb4.imi.jumpup.settings.PersistenceSettings;
import de.htw.fb4.imi.jumpup.trip.entities.Trip;
import de.htw.fb4.imi.jumpup.user.entities.User;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 19.01.2015
 *
 */
@Stateless(name = BeanNames.WEBSITE_TRIP_QUERY)
public class WebsiteTripQuery implements TripQueryMethod
{
    @PersistenceUnit(unitName = PersistenceSettings.PERSISTENCE_UNIT)
    protected EntityManagerFactory entityManagerFactory;

    /**
     * Create fresh entity manager.
     * @return
     */
    protected EntityManager getFreshEntityManager()
    {
        EntityManager em = this.entityManagerFactory.createEntityManager();

        return em;
    }    
    
    /* (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.trip.query.TripQueryMethod#getOfferedTrips(de.htw.fb4.imi.jumpup.user.entities.User)
     */
    @Override
    public List<Trip> getOfferedTrips(final User user)
    {
        this.prepareEntityManager(user);        
        return user.getOfferedTrips();
    }

    private void prepareEntityManager(User user)
    {
        // prepare for transaction
        EntityManager em = this.getFreshEntityManager();
        
        em.merge(user);        
    }

}
