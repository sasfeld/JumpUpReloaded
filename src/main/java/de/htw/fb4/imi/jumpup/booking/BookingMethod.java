/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.booking;

import javax.ejb.Local;

import de.htw.fb4.imi.jumpup.booking.entities.Booking;
import de.htw.fb4.imi.jumpup.trip.entities.Trip;
import de.htw.fb4.imi.jumpup.util.ErrorPrintable;

/**
 * <p>Business interface defining the core functionality to access our bookings.</p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 29.01.2015
 *
 */
@Local
public interface BookingMethod extends ErrorPrintable
{

    /**
     * Get a {@link Trip} entity by ID.
     * @param id
     * @return
     */
    Trip getTripByID(long id);
    
    /**
     * Prepare and persist the given booking.
     * 
     * Also trigger e-Mail notifcations.
     * @param booking
     * @param trip
     * @return
     */
    void createBooking(Booking booking, Trip trip);
    
    /**
     * Send the booking confirmation mail to the passenger.
     * @param booking
     */
    void sendBookingConfirmationToPassenger(Booking booking);
    
    /**
     * Send the booking information mail to the driver.
     * @param booking
     */
    void sendBookingInformationToDriver(Booking booking);
}
