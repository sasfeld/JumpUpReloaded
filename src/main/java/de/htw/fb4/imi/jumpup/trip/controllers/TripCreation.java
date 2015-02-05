/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.trip.controllers;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceUnit;

import de.htw.fb4.imi.jumpup.Application;
import de.htw.fb4.imi.jumpup.Application.LogType;
import de.htw.fb4.imi.jumpup.controllers.AbstractFacesController;
import de.htw.fb4.imi.jumpup.navigation.NavigationOutcomes;
import de.htw.fb4.imi.jumpup.settings.BeanNames;
import de.htw.fb4.imi.jumpup.settings.PersistenceSettings;
import de.htw.fb4.imi.jumpup.trip.creation.TripCreationMethod;
import de.htw.fb4.imi.jumpup.trip.entities.Trip;
import de.htw.fb4.imi.jumpup.trip.util.ConfigReader;
import de.htw.fb4.imi.jumpup.user.controllers.Login;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 12.01.2015
 *
 */
@Named(value = BeanNames.TRIP_CREATION_CONTROLLER)
@RequestScoped
public class TripCreation extends AbstractFacesController
{
    @Inject
    protected TripCreationMethod tripCreationMethod;
    protected Trip trip;    
   
    @PersistenceUnit(unitName = PersistenceSettings.PERSISTENCE_UNIT)
    protected EntityManagerFactory entityManagerFactory;
    
    @Inject
    protected Login loginController;
    
    @EJB(beanName = BeanNames.TRIP_CONFIG_READER)
    protected ConfigReader tripConfigReader;
    
    
    /**
     * Trip ID (used in edit mode only).
     */
    protected Long tripId;
    
    protected EntityManager getFreshEntityManager()
    {
        return this.entityManagerFactory.createEntityManager();
    }
    
    /**
     * @return the tripConfigReader
     */
    public ConfigReader getTripConfigReader()
    {
        return tripConfigReader;
    }

    /**
     * @param tripConfigReader the tripConfigReader to set
     */
    public void setTripConfigReader(ConfigReader tripConfigReader)
    {
        this.tripConfigReader = tripConfigReader;
    }

    
    /**
     * Get {@link Trip}.
     * 
     * Load from DB if the tripID was set, or create new.
     * 
     * @return
     */
    public Trip getTrip()
    {
        if (null != this.tripId && null == this.trip) {
            try {
                // Load from DB
                Application.log("TripCreation: tripId " + tripId + "given, trying to load from DB...", LogType.DEBUG, getClass());
                this.trip = this.getFreshEntityManager().find(Trip.class, this.tripId);
                
                // ensure that the trip belongs to the logged-in customer
                if (this.tripDoesNotBelongToLoggedInCustomer(trip)) {
                    Application.log("TripCreation: the customer " + loginController.getLoginModel().getCurrentUser().getIdentity() 
                            + " tried to manipulate a trip that doesn't belong to him. Trip ID: " + trip.getIdentity(), LogType.CRITICAL, getClass());
                    this.trip = null;
                }
            } catch (EntityNotFoundException e) {
                // not found - switch to Add mode
                this.trip = null;
            }
        } 
        
        if (null == this.trip) {
            Application.log("TripCreation: No tripId given, creating new...", LogType.DEBUG, getClass());
            this.trip = new Trip();
        }
        
        return this.trip;
    }
    
    private boolean tripDoesNotBelongToLoggedInCustomer(Trip trip)
    {
        return !(loginController.getLoginModel().getCurrentUser().getIdentity()
                == trip.getDriver().getIdentity());
    }

    /**
     * @return the tripId
     */
    public Long getTripId()
    {        
        return tripId;
    }

    /**
     * @param tripId the tripId to set
     */
    public void setTripId(Long tripId)
    {
        this.tripId = tripId;
    }

    /**
     * Action for adding a given trip.
     * @return
     */
    public String addTrip()
    {
        try {
            Application.log("TripCreation: try to add trip",
                    LogType.DEBUG, getClass());
            tripCreationMethod.addTrip(getTrip());
            
            if (tripCreationMethod.hasError()) {
                for (String error : tripCreationMethod.getErrors()) {
                    this.addDisplayErrorMessage(error);
                }
            } else {
                addDisplayInfoMessage("Your trip was added.");
            }

        } catch (Exception e) {
            Application.log("UserDetailsController: " + e.getMessage(),
                    LogType.ERROR, getClass());
            this.addDisplayErrorMessage("Could not add you trip. Please contact our customer care team.");
        }

        return NavigationOutcomes.TO_ADD_TRIP;
    }
    
    /**
     * Action for editing a trip, only if the trip ID is given.
     * @return
     */
    public String editTrip()
    {
        // no trip ID given
        if (null == this.getTripId()) {
            return NavigationOutcomes.TO_LIST_OFFERED_TRIPS;
        }
        
        try {
            Application.log("TripCreation: try to edit trip",
                    LogType.DEBUG, getClass());
            // we have to set the trip ID again, because it is lost for some reason
            this.getTrip().setIdentity(this.getTripId());
            
            tripCreationMethod.changeTrip(getTrip());
            
            if (tripCreationMethod.hasError()) {
                for (String error : tripCreationMethod.getErrors()) {
                    this.addDisplayErrorMessage(error);
                }
            } else {
                addDisplayInfoMessage("Your trip was edited.");
            }

        } catch (Exception e) {
            Application.log("UserDetailsController: " + e.getMessage(),
                    LogType.ERROR, getClass());
            this.addDisplayErrorMessage("Could not add your trip. Please contact our customer care team.");
        }

        return NavigationOutcomes.TO_EDIT_TRIP;
    }

    /**
     * Action for trip cancelation (soft-deletion), only if the trip ID is given.
     * @return
     */
   public String cancelTrip()
   {
       // no trip ID given
       if (null == this.getTripId()) {
           return NavigationOutcomes.TO_LIST_OFFERED_TRIPS;
       }
       
       try {
           Application.log("TripCreation: try to cancel trip",
                   LogType.DEBUG, getClass());
           // we have to set the trip ID again, because it is lost for some reason
           this.getTrip().setIdentity(this.getTripId());
           
           tripCreationMethod.cancelTrip(getTrip());
           
           if (tripCreationMethod.hasError()) {
               for (String error : tripCreationMethod.getErrors()) {
                   this.addDisplayErrorMessage(error);
               }
           } else {
               addDisplayInfoMessage("Your trip was canceled.");
           }

       } catch (Exception e) {
           Application.log("UserDetailsController: " + e.getMessage(),
                   LogType.ERROR, getClass());
           this.addDisplayErrorMessage("Could not cancel your trip. Please contact our customer care team.");
       }

       return NavigationOutcomes.TO_LIST_OFFERED_TRIPS;
   }
    
    

}
