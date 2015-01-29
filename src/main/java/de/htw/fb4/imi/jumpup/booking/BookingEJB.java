package de.htw.fb4.imi.jumpup.booking;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import de.htw.fb4.imi.jumpup.trip.entities.Trip;

@Stateless
public class BookingEJB
{
    @PersistenceContext
    private EntityManager em;

    public Trip getTripByID(long id)
    {
        final Query query = this.em.createNamedQuery("TRIP_GET_BY_ID",
                Trip.class);
        query.setParameter("identity", new Long(id));
        return (Trip) query.getSingleResult();

    }
}
