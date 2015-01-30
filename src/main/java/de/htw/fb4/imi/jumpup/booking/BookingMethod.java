/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.booking;

import javax.ejb.Local;

import de.htw.fb4.imi.jumpup.booking.entities.Booking;
import de.htw.fb4.imi.jumpup.trip.entities.Trip;
import de.htw.fb4.imi.jumpup.user.entities.User;
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
    void sendBookingCreationMailToPassenger(Booking booking);
    
    /**
     * Send the booking information mail to the driver.
     * @param booking
     */
    void sendBookingInformationMailToDriver(Booking booking, User driver);
    
    /**
     * Driver confirms the booking.
     * @param bookingId
     */
    void confirmBooking(Booking bookingId);
    
    /**
     * Send booking confirmation mail to passenger.
     * @param booking
     */
    void sendBookingConfirmationMailToPassenger(Booking booking);
    
    /**
     * Driver cancels a passenger's booking.
     * @param booking
     */
    void cancelBooking(Booking booking);
    
    /**
     * Send booking cancellation mail to passenger.
     * @param booking
     */
    void sendBookingCancelationMailToPassenger(Booking booking);
}