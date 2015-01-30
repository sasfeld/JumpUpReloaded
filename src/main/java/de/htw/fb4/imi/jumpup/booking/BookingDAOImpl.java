/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.booking;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import de.htw.fb4.imi.jumpup.booking.entities.Booking;
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
@Stateless(name = BeanNames.BOOKING_DAO)
public class BookingDAOImpl implements BookingDAO
{
    @PersistenceContext(unitName = PersistenceSettings.PERSISTENCE_UNIT)
    protected EntityManager em;

    /* (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.booking.BookingDAO#queryById(long)
     */
    @Override
    public Booking queryById(long bookingId)
    {
        final Query query = this.em.createNamedQuery(Booking.NAME_QUERY_BY_ID,
                Booking.class);
        query.setParameter("identity", new Long(bookingId));
        return (Booking) query.getSingleResult();
    }

    @Override
    /*
     * (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.booking.BookingDAO#save(de.htw.fb4.imi.jumpup.booking.entities.Booking)
     */
    public void save(Booking booking)
    {
        this.em.persist(this.em.contains(booking) ? booking : this.em.merge(booking));
        
        this.em.flush();
    }

    @Override
    /*
     * (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.booking.BookingDAO#getBookingsByTrip(de.htw.fb4.imi.jumpup.trip.entities.Trip)
     */
    public List<Booking> getBookingsByTrip(Trip trip)
    {
        final Query query = this.em.createNamedQuery(Booking.NAME_QUERY_BY_TRIP,
            Booking.class);
        query.setParameter("trip", trip);
        return query.getResultList(); 
    }

}
