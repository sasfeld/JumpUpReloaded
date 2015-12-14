/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.booking.controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import de.htw.fb4.imi.jumpup.Application;
import de.htw.fb4.imi.jumpup.Application.LogType;
import de.htw.fb4.imi.jumpup.booking.BookingDAO;
import de.htw.fb4.imi.jumpup.booking.entities.Booking;
import de.htw.fb4.imi.jumpup.controllers.AbstractFacesController;
import de.htw.fb4.imi.jumpup.settings.BeanNames;
import de.htw.fb4.imi.jumpup.user.login.LoginSession;

/**
 * <p>Controller for passenger's booked trips.</p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 01.02.2015
 *
 */
@SessionScoped
@Named(value = BeanNames.BOOKING_LIST_CONTROLLER)
public class BookingListController extends AbstractFacesController implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 7071380102601903431L;

    @Inject
    protected BookingDAO bookingDAO;
    
    @Inject
    protected LoginSession loginSession;
    
    protected List<Booking> passengerBookings;

    /**
     * @return the passengerBookings
     */
    public List<Booking> getPassengerBookings()
    {
        if (null == this.passengerBookings) {
            this.refresh();
            
            Application.log("getPassengerBookings(): " + this.passengerBookings, LogType.DEBUG, getClass());
        }
        
        return this.passengerBookings;
    }
    
    /**
     * Refresh bookings after DB changes.
     */
    public void refresh()
    {
        this.passengerBookings = this.bookingDAO.getBookingsByPassenger(loginSession.getCurrentUser());
    }
}
