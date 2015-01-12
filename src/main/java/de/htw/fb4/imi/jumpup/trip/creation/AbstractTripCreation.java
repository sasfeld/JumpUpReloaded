/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.trip.creation;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.resource.spi.IllegalStateException;

import de.htw.fb4.imi.jumpup.Application;
import de.htw.fb4.imi.jumpup.Application.LogType;
import de.htw.fb4.imi.jumpup.mail.MailAdapter;
import de.htw.fb4.imi.jumpup.mail.builder.MailBuilder;
import de.htw.fb4.imi.jumpup.settings.PersistenceSettings;
import de.htw.fb4.imi.jumpup.translate.Translatable;
import de.htw.fb4.imi.jumpup.trip.entities.Trip;
import de.htw.fb4.imi.jumpup.user.controllers.Login;
import de.htw.fb4.imi.jumpup.user.entities.User;
import de.htw.fb4.imi.jumpup.util.ErrorPrintable;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 12.01.2015
 *
 */
public class AbstractTripCreation implements TripCreationMethod, ErrorPrintable
{
    @PersistenceUnit(unitName = PersistenceSettings.PERSISTENCE_UNIT)
    protected EntityManagerFactory entityManagerFactory;
    
    @Inject
    protected MailBuilder mailBuilder;
    
    @Inject
    protected MailAdapter mailAdapter;  
    
    @Inject
    protected Translatable translator;
    
    @Inject
    protected Login loginController;
    
    protected Set<String> errorMessages;
    
    /**
     * Create fresh entity manager.
     * @return
     */
    protected EntityManager getFreshEntityManager()
    {
        EntityManager em = this.entityManagerFactory.createEntityManager();

        return em;
    }
    
    /**
     * Reset current state.
     */
    protected void reset()
    {
        this.errorMessages = new HashSet<>();
    }
    
    /**
     * 
     * @return
     * @throws IllegalStateException
     */
    protected User getCurrentUser() throws IllegalStateException
    {
        if (null == this.loginController) {
            throw new IllegalStateException("loginController was not injected.");
        }
        
        return this.loginController.getLoginModel().getCurrentUser();
    }
   
    /* (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.util.ErrorPrintable#hasError()
     */
    @Override
    public boolean hasError()
    {
        return (getErrors().length > 0);
    }

    /* (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.util.ErrorPrintable#getErrors()
     */
    @Override
    public String[] getErrors()
    {
        return this.errorMessages.toArray(new String[this.errorMessages.size()]);
    }

    /* (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.util.ErrorPrintable#getSingleErrorString()
     */
    @Override
    public String getSingleErrorString()
    {
        // return first element if given
        String[] errors = this.getErrors();
        
        if (errors.length > 0 ) {
            return errors[0];
        }
        
        return "";
    }

    /* (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.trip.creation.TripCreationMethod#addTrip(de.htw.fb4.imi.jumpup.trip.entities.Trip)
     */
    @Override
    public void addTrip(final Trip trip)
    {
        try {
            this.persistTrip(trip);
            this.sendTripAddedMailToDriver(trip);
        } catch ( Exception e ) {
            Application.log("addTrip(): exception" + e.getMessage(), LogType.ERROR, getClass());
            this.errorMessages.add("We could not add the trip. Please inform our customer care team.");
        }
    }

    private void sendTripAddedMailToDriver(final Trip trip)
    {
        // TODO Auto-generated method stub
        
    }

    protected void persistTrip(final Trip trip) throws IllegalStateException
    {
        EntityManager entityManager = this.getFreshEntityManager();
        User currentUser = this.getCurrentUser();
        
        // set driver to current logged in user and persist trip
        trip.setDriver(currentUser);
        entityManager.persist(trip);        
        entityManager.flush();
    }
   

    /* (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.trip.creation.TripCreationMethod#changeTrip(de.htw.fb4.imi.jumpup.trip.entities.Trip)
     */
    @Override
    public void changeTrip(final Trip trip)
    {
        try {
            this.updateTrip(trip);
            this.sendTripUpdatedMailToDriver(trip);
            this.sendTripUpdatedMailToPassengers(trip);
        } catch ( Exception e ) {
            Application.log("changeTrip(): exception" + e.getMessage(), LogType.ERROR, getClass());
            this.errorMessages.add("We could not change the trip. Please inform our customer care team.");
        }
    }   

    private void updateTrip(Trip trip)
    {
        EntityManager entityManager = this.getFreshEntityManager();
        
        entityManager.merge(trip);        
        entityManager.flush();
    }
    
    private void sendTripUpdatedMailToDriver(Trip trip)
    {
        // TODO Auto-generated method stub
        
    }

    private void sendTripUpdatedMailToPassengers(Trip trip)
    {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.trip.creation.TripCreationMethod#cancelTrip(de.htw.fb4.imi.jumpup.trip.entities.Trip)
     */
    @Override
    public void cancelTrip(final Trip trip)
    {
        try {
            this.softDeleteTrip(trip);
            this.sendTripCanceledMailToDriver(trip);
            this.sendTripCanceledMailToPassengers(trip);
        } catch ( Exception e ) {
            Application.log("cancelTrip(): exception" + e.getMessage(), LogType.ERROR, getClass());
            this.errorMessages.add("We could not cancel the trip. Please inform our customer care team.");
        }
    }
  
    /**
     * Soft-Delete means: only cancel the trip. It will not appear in the listings anymore, but the entity still exists.
     * @param trip
     */
    private void softDeleteTrip(Trip trip)
    {        
        EntityManager entityManager = this.getFreshEntityManager();
        
        trip.setCancelationDateTime(this.getCurrentTimestamp());    
        
        entityManager.merge(trip);
        entityManager.flush();
    }    
  

    private void sendTripCanceledMailToDriver(Trip trip)
    {
        // TODO Auto-generated method stub
        
    }
    
    private void sendTripCanceledMailToPassengers(Trip trip)
    {
        // TODO Auto-generated method stub
        
    }
    
    private Timestamp getCurrentTimestamp()
    {
        return new Timestamp(new Date().getTime());
    }
}
