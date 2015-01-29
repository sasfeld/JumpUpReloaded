package de.htw.fb4.imi.jumpup.booking;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import de.htw.fb4.imi.jumpup.booking.entities.Booking;
import de.htw.fb4.imi.jumpup.trip.entities.Trip;
import de.htw.fb4.imi.jumpup.user.controllers.Login;

@Stateless
public class BookingEJB
{
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private Login loginController;


    public Trip getTripByID(long id)
    {
        final Query query = this.em.createNamedQuery("TRIP_GET_BY_ID",
                Trip.class);
        query.setParameter("identity", new Long(id));
        return (Trip) query.getSingleResult();

    }
    
    public void createBooking(Booking booking, long tripId)
    {
        booking.setPassenger(loginController.getLoginModel().getCurrentUser());

        this.getTripByID(tripId);
    }

}
