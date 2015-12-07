/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.trip.rest.models;

import java.util.ArrayList;
import java.util.Collection;

import de.htw.fb4.imi.jumpup.rest.IEntityMapper;
import de.htw.fb4.imi.jumpup.trip.entities.Trip;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 02.12.2015
 *
 */
public class TripEntityMapper implements IEntityMapper<TripWebServiceModel, Trip>
{

    @Override
    public TripWebServiceModel mapEntity(Trip entity)
    {
        if (! (entity instanceof de.htw.fb4.imi.jumpup.trip.entities.Trip)) {
            throw new IllegalArgumentException("Type of entity must be Trip");
        }
        
        TripWebServiceModel webServiceTrip = new TripWebServiceModel();
        de.htw.fb4.imi.jumpup.trip.entities.Trip entityTrip = (de.htw.fb4.imi.jumpup.trip.entities.Trip) entity;
       
        webServiceTrip.setIdentity(entityTrip.getIdentity());
        webServiceTrip.setCreationTimestamp(entityTrip.getCreationTimestamp());
        webServiceTrip.setUpdateTimestamp(entityTrip.getUpdateTimestamp());
        webServiceTrip.setStartpoint(entityTrip.getStartpoint());
        webServiceTrip.setEndpoint(entityTrip.getEndpoint());
        webServiceTrip.setLatStartpoint(entityTrip.getLatStartpoint());
        webServiceTrip.setLongStartpoint(entityTrip.getLongEndpoint());
        webServiceTrip.setLatEndpoint(entityTrip.getLatEndpoint());
        webServiceTrip.setLongEndpoint(entityTrip.getLongEndpoint());
        webServiceTrip.setStartDateTime(entityTrip.getStartDateTime());
        webServiceTrip.setEndDateTime(entityTrip.getEndDateTime());
        webServiceTrip.setPrice(entityTrip.getPrice());
        webServiceTrip.setOverViewPath(entityTrip.getOverViewPath());
        webServiceTrip.setViaWaypoints(entityTrip.getViaWaypoints());
        webServiceTrip.setNumberOfSeats(entityTrip.getNumberOfSeats());
        webServiceTrip.setVehicle(entityTrip.getVehicle());
//        webServiceTrip.setDriver(entityTrip.getDriver());
        webServiceTrip.setCancelationDateTime(entityTrip.getCancelationDateTime());
        webServiceTrip.setDistanceMeters(entityTrip.getDistanceMeters());
        webServiceTrip.setDurationSeconds(entityTrip.getDurationSeconds());       
        
        return webServiceTrip;
    }

    @Override
    public Trip mapWebServiceModel(TripWebServiceModel webServiceModel)
    {
        Trip entityTrip = new de.htw.fb4.imi.jumpup.trip.entities.Trip();
        
        entityTrip.setIdentity(webServiceModel.getIdentity());
        entityTrip.setCreationTimestamp(webServiceModel.getCreationTimestamp());
        entityTrip.setUpdateTimestamp(webServiceModel.getUpdateTimestamp());
        entityTrip.setStartpoint(webServiceModel.getStartpoint());
        entityTrip.setEndpoint(webServiceModel.getEndpoint());
        entityTrip.setLatStartpoint(webServiceModel.getLatStartpoint());
        entityTrip.setLongStartpoint(webServiceModel.getLongEndpoint());
        entityTrip.setLatEndpoint(webServiceModel.getLatEndpoint());
        entityTrip.setLongEndpoint(webServiceModel.getLongEndpoint());
        entityTrip.setStartDateTime(webServiceModel.getStartDateTime());
        entityTrip.setEndDateTime(webServiceModel.getEndDateTime());
        entityTrip.setPrice(webServiceModel.getPrice());
        entityTrip.setOverViewPath(webServiceModel.getOverViewPath());
        entityTrip.setViaWaypoints(webServiceModel.getViaWaypoints());
        entityTrip.setNumberOfSeats(webServiceModel.getNumberOfSeats());
        entityTrip.setVehicle(webServiceModel.getVehicle());
//        webServiceTrip.setDriver(entityTrip.getDriver());
        entityTrip.setCancelationDateTime(webServiceModel.getCancelationDateTime());
        entityTrip.setDistanceMeters(webServiceModel.getDistanceMeters());
        entityTrip.setDurationSeconds(webServiceModel.getDurationSeconds());      
        
        return entityTrip;
    }

    @Override
    /*
     * (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.rest.IEntityMapper#mapEntities(java.util.Collection)
     */
    public Collection<TripWebServiceModel> mapEntities(Collection<Trip> entityTypes)
    {
        Collection<TripWebServiceModel> tripWebServiceCollection = new ArrayList<TripWebServiceModel>();
        
        for (Trip trip : entityTypes) {
            tripWebServiceCollection.add(this.mapEntity(trip));
        }
        
        return tripWebServiceCollection;
    }
}
