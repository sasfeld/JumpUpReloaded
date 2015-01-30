package de.htw.fb4.imi.jumpup.booking.controller;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;

import de.htw.fb4.imi.jumpup.Application;
import de.htw.fb4.imi.jumpup.Application.LogType;
import de.htw.fb4.imi.jumpup.booking.BookingMethod;
import de.htw.fb4.imi.jumpup.booking.entities.Booking;
import de.htw.fb4.imi.jumpup.controllers.AbstractFacesController;
import de.htw.fb4.imi.jumpup.settings.BeanNames;
import de.htw.fb4.imi.jumpup.trip.TripDAO;
import de.htw.fb4.imi.jumpup.trip.entities.Trip;
import de.htw.fb4.imi.jumpup.user.UserDAO;
import de.htw.fb4.imi.jumpup.user.entities.User;

/**
 * 
 * <p>
 * Controller classes for bookings.
 * </p>
 * 
 * @author <a href="mailto:m_seidler@hotmail.de">Marco Seidler</a>
 * @since 29.01.2015
 * 
 */

@Named(value = BeanNames.BOOKING_CONTROLLER)
@RequestScoped
public class BookingController extends AbstractFacesController implements
        Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = -4989295237379145060L;

    @Inject
    private BookingMethod bookingEJB;
    
    @Inject
    private TripDAO tripDAO;
    
    @Inject
    private UserDAO userDAO;

    private long tripId;
    
    protected Trip trip;    
    
    protected User driver;

    private Booking booking = new Booking();

    /**
     * Bind-booking-data action.
     * @return
     */
    public String bindBookingData()
    {
        try {
            bookingEJB.createBooking(this.getBooking(), this.getTrip());
            
            if (bookingEJB.hasError()) {
                // show first error message
                this.addDisplayErrorMessage(bookingEJB.getErrors()[0]);
                
                return null;
            }
            
            // try to send mail
            bookingEJB.sendBookingCreationMailToPassenger(this.getBooking());
            
            if (bookingEJB.hasError()) {
                addDisplayErrorMessage("Your booking was successful, but we couldn't send a confirmation mail. Please refer to your 'My bookings' page.");
                return null;
            }
            
            bookingEJB.sendBookingInformationMailToDriver(this.getBooking(), this.getDriver());            

            // no error, redirect to booking page
            addDisplayInfoMessage("Your booking was successful! You will recieve an eMail with further information and are able to view the details in your booking overview.");
        } catch (Exception e) {
            Application.log(e.getMessage(), LogType.ERROR, getClass());
            addDisplayErrorMessage("Your given values aren't valid.");
        }

        return null;
    }

    public Booking getBooking()
    {
        return booking;
    }

    public void setBooking(Booking booking)
    {
        this.booking = booking;
    }

    public long getTripId()
    {
        return tripId;
    }
    
    public User getDriver()
    {
        if (null == this.driver) {
            try {
                this.driver = this.userDAO.loadById(this.getTrip().getDriver().getIdentity());
            } catch (NoResultException e) {
                this.addDisplayErrorMessage("Could not find any matching driver.");
                return null;
            }
        }
        
        return this.driver;
    }
    
    public Trip getTrip()
    {
        if (null == this.trip) {
           try {
               this.trip =  this.tripDAO.getTripByID(this.getTripId());
           } catch (NoResultException e) {
               this.addDisplayErrorMessage("Could not find any matching trip.");
               return null;
           }      
        }
        
        return this.trip;
    }

    public void setTripId(long tripId)
    {
        Application.log("Setting tripID " + tripId, LogType.DEBUG, getClass());
        this.tripId = tripId;
    }

}
