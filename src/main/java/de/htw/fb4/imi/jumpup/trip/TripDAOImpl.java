/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.trip;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import de.htw.fb4.imi.jumpup.settings.BeanNames;
import de.htw.fb4.imi.jumpup.settings.PersistenceSettings;
import de.htw.fb4.imi.jumpup.trip.entities.Trip;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 30.01.2015
 *
 */
@Stateless(name = BeanNames.TRIP_DAO)
public class TripDAOImpl implements TripDAO
{
    @PersistenceContext(unitName = PersistenceSettings.PERSISTENCE_UNIT)
    protected EntityManager em;

    /*
     * (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.trip.TripDAO#getTripByID(long)
     */
    public Trip getTripByID(long identity)
    {
        final Query query = this.em.createNamedQuery(Trip.NAME_QUERY_BY_ID,
                Trip.class);
        query.setParameter("identity", new Long(identity));
        return (Trip) query.getSingleResult();

    }
}
