/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.trip.restservice;

import java.sql.Timestamp;
import java.util.Date;

import de.htw.fb4.imi.jumpup.trip.entities.Trip;
import de.htw.fb4.imi.jumpup.trip.restservice.model.SingleTripQueryResult;
import de.htw.fb4.imi.jumpup.user.entities.User;
import de.htw.fb4.imi.jumpup.verhicle.entities.Vehicle;

/**
 * <p>Factory for creating models that will be returned by our REST services.</p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 26.01.2015
 *
 */
public class QueryResultFactory
{
    /**
     * Build a {@link SingleTripQueryResult} which will be returned marshalled within our REST services.
     * 
     * @param trip entity from database
     * @return
     */
    public static SingleTripQueryResult newSingleTripQueryResult(final Trip trip)
    {
        SingleTripQueryResult singleTripQueryResult = new SingleTripQueryResult();
        
        fillFromTripEntity(singleTripQueryResult, trip);
        fillFromDriverEntity(singleTripQueryResult, trip.getDriver());
        fillFromVehicleEntity(singleTripQueryResult, trip.getVehicle());     
        
        return singleTripQueryResult;
    }
    
    private static void fillFromTripEntity(
            SingleTripQueryResult singleTripQueryResult, Trip trip)
    {
        singleTripQueryResult.getTrip().setStartpoint(trip.getStartpoint());
        singleTripQueryResult.getTrip().setEndpoint(trip.getEndpoint());
        singleTripQueryResult.getTrip().setLatStartpoint(trip.getLatStartpoint());
        singleTripQueryResult.getTrip().setLongStartpoint(trip.getLongStartpoint());
        singleTripQueryResult.getTrip().setLatEndpoint(trip.getLatEndpoint());
        singleTripQueryResult.getTrip().setLongEndpoint(trip.getLongEndpoint());
        singleTripQueryResult.getTrip().setStartDateTime(toTimestamp(trip.getStartDateTime()));
        singleTripQueryResult.getTrip().setEndDateTime(toTimestamp(trip.getEndDateTime()));
        singleTripQueryResult.getTrip().setPrice(trip.getPrice());
        singleTripQueryResult.getTrip().setViaWaypoints(trip.getViaWaypoints());
        singleTripQueryResult.getTrip().setNumberOfSeats(trip.getNumberOfSeats());       
        
    }
    
    private static void fillFromDriverEntity(
            SingleTripQueryResult singleTripQueryResult, User driver)
    {
        singleTripQueryResult.getDriver().setUsername(driver.getUsername());
        singleTripQueryResult.getDriver().setEmail(driver.geteMail());
        singleTripQueryResult.getDriver().setCountry(driver.getCountry());
        singleTripQueryResult.getDriver().setGender(driver.getUserDetails().getGender());
        singleTripQueryResult.getDriver().setMobileNumber(driver.getUserDetails().getMobileNumber());
        singleTripQueryResult.getDriver().setPrename(driver.getPrename());
        singleTripQueryResult.getDriver().setLastname(driver.getLastname());
        singleTripQueryResult.getDriver().setSkype(driver.getUserDetails().getSkype());
        singleTripQueryResult.getDriver().setSpokenLanguages(driver.getUserDetails().getLanguages());
        singleTripQueryResult.getDriver().setTown(driver.getTown());        
    }
    
    /**
     * TODO implement
     * @param singleTripQueryResult
     * @param vehicle
     */
    private static void fillFromVehicleEntity(
            SingleTripQueryResult singleTripQueryResult, Vehicle vehicle)
    {
        // TODO Auto-generated method stub
        // only dummy implementation
        singleTripQueryResult.getVehicle().setManufactor("BMW");
    }

    private static Timestamp toTimestamp(Date startDateTime)
    {
        return new Timestamp(startDateTime.getTime());
    }

}
