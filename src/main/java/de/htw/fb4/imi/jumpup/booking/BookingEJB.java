/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.booking;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.lang.exception.ExceptionUtils;

import de.htw.fb4.imi.jumpup.Application;
import de.htw.fb4.imi.jumpup.Application.LogType;
import de.htw.fb4.imi.jumpup.booking.entities.Booking;
import de.htw.fb4.imi.jumpup.mail.MailAdapter;
import de.htw.fb4.imi.jumpup.mail.MailModel;
import de.htw.fb4.imi.jumpup.mail.builder.MailBuilder;
import de.htw.fb4.imi.jumpup.settings.BeanNames;
import de.htw.fb4.imi.jumpup.translate.Translatable;
import de.htw.fb4.imi.jumpup.trip.entities.Trip;
import de.htw.fb4.imi.jumpup.trip.restservice.QueryResultFactory;
import de.htw.fb4.imi.jumpup.trip.restservice.model.TripSearchCriteria;
import de.htw.fb4.imi.jumpup.trip.util.ConfigReader;
import de.htw.fb4.imi.jumpup.trip.util.TripAndBookingsConfigKeys;
import de.htw.fb4.imi.jumpup.user.controllers.Login;
import de.htw.fb4.imi.jumpup.user.entities.User;
import de.htw.fb4.imi.jumpup.util.FileUtil;

@Stateless(name = BeanNames.BOOKING_EJB)
public class BookingEJB implements BookingMethod
{
    @Inject
    private Login loginController;

    @Inject
    protected QueryResultFactory queryResultFactory;
    
    @Inject
    protected MailBuilder mailBuilder;
    
    @Inject
    protected MailAdapter mailAdapter;  
    
    @Inject
    protected Translatable translator;
    
    @EJB(beanName = BeanNames.TRIP_CONFIG_READER)
    protected ConfigReader tripConfigReader;
    
    @Inject
    protected BookingDAO bookingDAO;

    protected List<String> errors;

    protected void reset()
    {
        this.errors = new ArrayList<String>();
    }

    @Override
    /*
     * (non-Javadoc)
     * 
     * @see
     * de.htw.fb4.imi.jumpup.booking.BookingMethod#createBooking(de.htw.fb4.
     * imi.jumpup.booking.entities.Booking, long)
     */
    public void createBooking(Booking booking, Trip trip)
    {
        this.reset();

        // The trip cannot be booked anymore
        this.loadBookings(trip);
        
        if (!trip.canBeBooked(getCurrentUser())) {
            this.errors
                    .add("We are sorry to tell you that this trip can't be booked anymore. Please search for other trips in our big community!");
            return;
        }

        // Hash check to make sure that the request parameters were not
        // manipulated
        if (!this.checkBookingHash(booking, trip)) {
            Application
                    .log("createBooking(): booking hash check showed that booking data was manipulated. User ID: "
                            + getCurrentUser().getIdentity()
                            + " Booking data: " + booking + " Trip: " + trip,
                            LogType.CRITICAL, getClass());

            this.errors
                    .add("The given booking data is invalid. Please return to the trip page and try again.");
            return;
        }

        this.completeBooking(booking, trip);
        this.persistBooking(booking);
    }

    private void loadBookings(Trip trip)
    {
        trip.setBookings(this.bookingDAO.getBookingsByTrip(trip)); 
     }

    private void persistBooking(Booking booking)
    {
        try {
            this.bookingDAO.save(booking);
        } catch (Exception e) {
            Application.log("persistBooking: exception " + e.getMessage()
                    + "\nStack trace:\n" + ExceptionUtils.getFullStackTrace(e),
                    LogType.ERROR, getClass());
            this.errors
                    .add("Could not save your booking. Please contact the customer care team.");
        }
    }

    private void completeBooking(Booking booking, Trip trip)
    {
        booking.setTrip(trip);
        booking.setPassenger(this.getCurrentUser());
    }

    /**
     * Check whether the POSTed booking hash matches the one calculated by the
     * related {@link TripSearchCriteria}.
     * 
     * @param booking
     * @param trip
     * @return
     */
    private boolean checkBookingHash(Booking booking, Trip trip)
    {
        TripSearchCriteria reconstructedSearchCriteria = this.queryResultFactory
                .newTripSearchCriteriaBy(booking);

        if (!booking.getBookingHash().equals(
                reconstructedSearchCriteria.createBookingHash(trip))) {
            return false;
        }

        return true;
    }

    protected User getCurrentUser()
    {
        return loginController.getLoginModel().getCurrentUser();
    }

    @Override
    /*
     * (non-Javadoc)
     * 
     * @see de.htw.fb4.imi.jumpup.booking.BookingMethod#
     * sendBookingConfirmationToPassenger
     * (de.htw.fb4.imi.jumpup.booking.entities.Booking)
     */
    public void sendBookingCreationMailToPassenger(Booking booking)
    {
        try {
            buildTxtMail(TripAndBookingsConfigKeys.JUMPUP_BOOKING_CREATED_MAIL_PASSENGER_TEMPLATE_TXT);
            MailModel m = this.mailBuilder.getBuildedMailModel()
                    .addRecipient(new InternetAddress(booking.getPassenger().geteMail()))
                    .setSubject(this.translator.translate("JumpUp.Me - Your booking request was sent to the driver"));
            
            this.mailAdapter.sendHtmlMail(m);
        } catch (AddressException e) {
            Application.log("sendBookingCreationMailToPassenger(): the recipient mail of the passenger is malformed. Will not set the sender.\nException: "
                    + e.getMessage() + "\n"
                    + ExceptionUtils.getFullStackTrace(e), LogType.ERROR, getClass());
            this.errors.add("We could not send the booking mail.");
        } catch (Exception e) {
            Application.log("sendBookingCreationMailToPassenger(): error while sending booking creation mail to passenger.\nException: "
                    + e.getMessage() + "\n"
                    + ExceptionUtils.getFullStackTrace(e), LogType.ERROR, getClass());
            this.errors.add("We could not send the booking mail.");
        }
    }

    @Override
    /*
     * (non-Javadoc)
     * 
     * @see
     * de.htw.fb4.imi.jumpup.booking.BookingMethod#sendBookingInformationToDriver
     * (de.htw.fb4.imi.jumpup.booking.entities.Booking)
     */
    public void sendBookingInformationMailToDriver(Booking booking, User driver)
    {
        try {
            buildTxtMail(TripAndBookingsConfigKeys.JUMPUP_BOOKING_CREATED_MAIL_DRIVER_TEMPLATE_TXT);
            MailModel m = this.mailBuilder.getBuildedMailModel()
                    .addRecipient(new InternetAddress(driver.geteMail()))
                    .setSubject(this.translator.translate("JumpUp.Me - You got one new booking request"));
            
            this.mailAdapter.sendHtmlMail(m);
        } catch (AddressException e) {
            Application.log("sendBookingInformationMailToDriver(): the recipient mail of the driver is malformed. Will not set the sender.\nException: "
                    + e.getMessage() + "\n"
                    + ExceptionUtils.getFullStackTrace(e), LogType.ERROR, getClass());
            this.errors.add("We could not send the booking mail.");
        } catch (Exception e) {
            Application.log("sendBookingInformationMailToDriver(): error while sending booking creation mail to driver.\nException: "
                    + e.getMessage() + "\n"
                    + ExceptionUtils.getFullStackTrace(e), LogType.ERROR, getClass());
            this.errors.add("We could not send the booking mail.");
        }
    }
    
    protected void buildTxtMail(String txtTemplateConfigKey)
    {
        File templateFile = new File(FileUtil.getPathToWebInfFolder(), this.tripConfigReader.fetchValue(txtTemplateConfigKey));
        this.mailBuilder.addPlainTextContent(templateFile);
    }

    @Override
    public boolean hasError()
    {
        return this.errors.size() > 0;
    }

    @Override
    public String[] getErrors()
    {
        return this.errors.toArray(new String[this.errors.size()]);
    }

    @Override
    public String getSingleErrorString()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    /*
     * (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.booking.BookingMethod#confirmBooking(de.htw.fb4.imi.jumpup.booking.entities.Booking)
     */
    public void confirmBooking(Booking booking)
    {
        this.reset();
        try {
            this.confirmBookingIfNotCanceledAndDoneYet(booking);
        } catch (Exception e) {
            this.errors.add("Error while trying to confirm the booking. Please try again.");
            Application.log("confirmBooking(): exception " + e.getMessage() + "\nStack trace:\n" + ExceptionUtils.getFullStackTrace(e), LogType.ERROR, getClass());
        }        
    }

    private void confirmBookingIfNotCanceledAndDoneYet(Booking booking)
    {
        if (!booking.wasCancelled() && !booking.wasConfirmed()) {
            booking.setConfirmationDateTime(this.getCurrentTimestamp());
            bookingDAO.save(booking);
            return;
        }        
        
        // booking cannot be confirmed
        this.errors.add("The booking cannot be confirmed because it was previously canceled or already booked.");
    }
    
    private Timestamp getCurrentTimestamp()
    {
        return new Timestamp(new Date().getTime());
    }

    @Override
    /*
     * (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.booking.BookingMethod#sendBookingConfirmationMailToPassenger(de.htw.fb4.imi.jumpup.booking.entities.Booking)
     */
    public void sendBookingConfirmationMailToPassenger(Booking booking)
    {
        this.reset();
        try {
            buildTxtMail(TripAndBookingsConfigKeys.JUMPUP_BOOKING_CONFIRMED_MAIL_PASSENGER_TEMPLATE_TXT);
            MailModel m = this.mailBuilder.getBuildedMailModel()
                    .addRecipient(new InternetAddress(booking.getPassenger().geteMail()))
                    .setSubject(this.translator.translate("JumpUp.Me - Your booking was confirmed by the driver"));
            
            this.mailAdapter.sendHtmlMail(m);
        } catch (AddressException e) {
            Application.log("sendBookingConfirmationMailToPassenger(): the recipient mail of the passenger is malformed. Will not set the sender.\nException: "
                    + e.getMessage() + "\n"
                    + ExceptionUtils.getFullStackTrace(e), LogType.ERROR, getClass());
            this.errors.add("We could not send the booking confirmation mail.");
        } catch (Exception e) {
            Application.log("sendBookingConfirmationMailToPassenger(): error while sending booking confirmation mail to the passenger.\nException: "
                    + e.getMessage() + "\n"
                    + ExceptionUtils.getFullStackTrace(e), LogType.ERROR, getClass());
            this.errors.add("We could not send the booking confirmation mail.");
        }        
    }

    @Override
    /*
     * (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.booking.BookingMethod#cancelBooking(de.htw.fb4.imi.jumpup.booking.entities.Booking)
     */
    public void cancelBooking(Booking booking)
    {
        this.reset();
        try {
            this.cancelBookingIfCanBeCancelled(booking);
        } catch (Exception e) {
            this.errors.add("Error while trying to cancel the booking. Please try again.");
            Application.log("cancelBooking(): exception " + e.getMessage() + "\nStack trace:\n" + ExceptionUtils.getFullStackTrace(e), LogType.ERROR, getClass());
        } 
    }

    private void cancelBookingIfCanBeCancelled(Booking booking)
    {
        if (!booking.wasCancelled()) {
             booking.setCancelationDateTime(this.getCurrentTimestamp());
             bookingDAO.save(booking);
                
             return;
        }
       
        this.errors.add("The booking can't be cancelled. Did you already cancel it?"); 
    }

    @Override
    /*
     * (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.booking.BookingMethod#sendBookingCancelationMailToPassenger(de.htw.fb4.imi.jumpup.booking.entities.Booking)
     */
    public void sendBookingCancelationMailToPassenger(Booking booking)
    {
        this.reset();
        
        try {
            buildTxtMail(TripAndBookingsConfigKeys.JUMPUP_BOOKING_CANCELED_MAIL_PASSENGER_TEMPLATE_TXT);
            MailModel m = this.mailBuilder.getBuildedMailModel()
                    .addRecipient(new InternetAddress(booking.getPassenger().geteMail()))
                    .setSubject(this.translator.translate("JumpUp.Me - Your booking was canceled by the driver"));
            
            this.mailAdapter.sendHtmlMail(m);
        } catch (AddressException e) {
            Application.log("sendBookingCancelationMailToPassenger(): the recipient mail of the passenger is malformed. Will not set the sender.\nException: "
                    + e.getMessage() + "\n"
                    + ExceptionUtils.getFullStackTrace(e), LogType.ERROR, getClass());
            this.errors.add("We could not send the booking cancelation mail.");
        } catch (Exception e) {
            Application.log("sendBookingCancelationMailToPassenger(): error while sending booking cancelation mail to the passenger.\nException: "
                    + e.getMessage() + "\n"
                    + ExceptionUtils.getFullStackTrace(e), LogType.ERROR, getClass());
            this.errors.add("We could not send the booking cancelation mail.");
        }                
    }

}
