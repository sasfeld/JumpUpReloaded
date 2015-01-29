/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.booking;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.exception.ExceptionUtils;

import de.htw.fb4.imi.jumpup.Application;
import de.htw.fb4.imi.jumpup.Application.LogType;
import de.htw.fb4.imi.jumpup.booking.entities.Booking;
import de.htw.fb4.imi.jumpup.trip.entities.Trip;
import de.htw.fb4.imi.jumpup.trip.restservice.QueryResultFactory;
import de.htw.fb4.imi.jumpup.trip.restservice.model.TripSearchCriteria;
import de.htw.fb4.imi.jumpup.user.controllers.Login;
import de.htw.fb4.imi.jumpup.user.entities.User;

@Stateless
public class BookingEJB implements BookingMethod
{
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private Login loginController;
    
    @Inject
    protected QueryResultFactory queryResultFactory;
    
    protected List<String> errors;

    protected void reset()
    {
        this.errors = new ArrayList<String>();
    }

    /**
     * Load the given trip by ID.
     * @param id
     * @return
     */
    public Trip getTripByID(long id)
    {
        final Query query = this.em.createNamedQuery(Trip.NAME_QUERY_BY_ID,
                Trip.class);
        query.setParameter("identity", new Long(id));
        return (Trip) query.getSingleResult();

    }
    
    @Override
    /*
     * (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.booking.BookingMethod#createBooking(de.htw.fb4.imi.jumpup.booking.entities.Booking, long)
     */
    public void createBooking(Booking booking, long tripId)
    {
        this.reset();    
        
        try {
            Trip trip =  this.getTripByID(tripId);
            
            if (!this.checkBookingHash(booking, trip)) {
                Application.log("createBooking(): booking hash check showed that booking data was manipulated. User ID: " + getCurrentUser().getIdentity()
                        + "Booking data: " + booking, LogType.CRITICAL, getClass());
                
                this.errors.add("The given booking data is invalid. Please return to the trip page and try again.");
                return;
            }
            
            this.completeBooking(booking, trip);
            this.persistBooking(booking);
        } catch (NoResultException e) {
            this.errors.add("Could not find any matching trip.");
            return;
        }       
    }

    private void persistBooking(Booking booking)
    {
        try {
            this.em.persist(booking);
            this.em.flush();
        } catch (Exception e) {
            Application.log("persistBooking: exception " + e.getMessage() + "\nStack trace:\n" + ExceptionUtils.getFullStackTrace(e), LogType.ERROR, getClass());
            this.errors.add("Could not save your booking. Please contact the customer care team.");
        }
    }

    private void completeBooking(Booking booking, Trip trip)
    {
        booking.setTrip(trip);
        booking.setPassenger(this.getCurrentUser());        
    }

    /**
     * Check whether the POSTed booking hash matches the one calculated by the related {@link TripSearchCriteria}.
     * @param booking
     * @param trip
     * @return
     */
    private boolean checkBookingHash(Booking booking, Trip trip)
    {
        TripSearchCriteria reconstructedSearchCriteria = this.queryResultFactory.newTripSearchCriteriaBy(booking);
        
        if (!booking.getBookingHash().equals(reconstructedSearchCriteria.createBookingHash(trip))) {
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
     * @see de.htw.fb4.imi.jumpup.booking.BookingMethod#sendBookingConfirmationToPassenger(de.htw.fb4.imi.jumpup.booking.entities.Booking)
     */
    public void sendBookingConfirmationToPassenger(Booking booking)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    /*
     * (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.booking.BookingMethod#sendBookingInformationToDriver(de.htw.fb4.imi.jumpup.booking.entities.Booking)
     */
    public void sendBookingInformationToDriver(Booking booking)
    {
        // TODO Auto-generated method stub
        
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

}
