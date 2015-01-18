/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.trip.controllers;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

import de.htw.fb4.imi.jumpup.Application;
import de.htw.fb4.imi.jumpup.Application.LogType;
import de.htw.fb4.imi.jumpup.controllers.AbstractFacesController;
import de.htw.fb4.imi.jumpup.navigation.NavigationOutcomes;
import de.htw.fb4.imi.jumpup.settings.BeanNames;
import de.htw.fb4.imi.jumpup.settings.PersistenceSettings;
import de.htw.fb4.imi.jumpup.trip.creation.TripCreationMethod;
import de.htw.fb4.imi.jumpup.trip.entities.Trip;

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
    
    protected EntityManager getFreshEntityManager()
    {
        return this.entityManagerFactory.createEntityManager();
    }
    
    /**
     * Trip ID (used in edit mode only).
     */
    protected Long tripId;
    
    
    public Trip getTrip()
    {
        if (null != this.tripId) {
            try {
                // Load from DB
                Application.log("TripCreation: tripId " + tripId + "given, trying to load from DB...", LogType.DEBUG, getClass());
                this.trip = this.getFreshEntityManager().find(Trip.class, this.tripId);
            } catch (EntityNotFoundException e) {
                // not found - switch to Add mode
                Application.log("TripCreation: could not find entity, creating new...", LogType.DEBUG, getClass());
                this.trip = new Trip();
            }
        } else if (null == this.trip) {
            Application.log("TripCreation: No tripId given, creating new...", LogType.DEBUG, getClass());
            this.trip = new Trip();
        }
        
        return this.trip;
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
        try {
            Application.log("TripCreation: try to edit trip",
                    LogType.DEBUG, getClass());
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
            this.addDisplayErrorMessage("Could not add you trip. Please contact our customer care team.");
        }

        return NavigationOutcomes.TO_EDIT_TRIP;
    }


   
    
    

}
