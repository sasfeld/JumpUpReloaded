/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.trip.restservice;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import de.htw.fb4.imi.jumpup.booking.entities.Booking;
import de.htw.fb4.imi.jumpup.navigation.NavigationBean;
import de.htw.fb4.imi.jumpup.settings.BeanNames;
import de.htw.fb4.imi.jumpup.translate.Translatable;
import de.htw.fb4.imi.jumpup.trip.entities.Trip;
import de.htw.fb4.imi.jumpup.trip.query.TripQueryNoResults;
import de.htw.fb4.imi.jumpup.trip.restservice.model.SingleTripQueryResult;
import de.htw.fb4.imi.jumpup.trip.restservice.model.TripQueryResults;
import de.htw.fb4.imi.jumpup.trip.restservice.model.TripQueryResults.Translations;
import de.htw.fb4.imi.jumpup.trip.restservice.model.TripSearchCriteria;
import de.htw.fb4.imi.jumpup.user.entities.User;
import de.htw.fb4.imi.jumpup.verhicle.entities.Vehicle;

/**
 * <p>Factory for creating models that will be returned by our REST services.</p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 26.01.2015
 *
 */
@Stateless(name = BeanNames.QUERY_RESULT_FACTORY)
public class QueryResultFactory
{
    @Inject
    protected Translatable translator;
    
    @Inject
    protected NavigationBean navigationHelper;
    
    /**
     * Build a {@link SingleTripQueryResult} which will be returned marshalled within our REST services.
     * 
     * @param trip entity from database
     * @return
     */
    public SingleTripQueryResult newSingleTripQueryResult(final Trip trip, TripSearchCriteria searchCriteria)
    {
        SingleTripQueryResult singleTripQueryResult = new SingleTripQueryResult();
        
        singleTripQueryResult.setTripSearchCriteria(searchCriteria);
        fillFromTripEntity(singleTripQueryResult, trip);
        fillFromDriverEntity(singleTripQueryResult, trip.getDriver());
        fillFromVehicleEntity(singleTripQueryResult, trip.getVehicle());     
        
        return singleTripQueryResult;
    }

    private void fillFromTripEntity(
            SingleTripQueryResult singleTripQueryResult, Trip trip)
    {        
        singleTripQueryResult.getTrip().setId(trip.getIdentity());
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
        // generate and set booking URL to enable booking this trip
        singleTripQueryResult.getTrip().setBookingUrl(navigationHelper.generateAddBookingUrl(singleTripQueryResult.getTripSearchCriteria(), trip));        
    }
    
    private void fillFromDriverEntity(
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
    private void fillFromVehicleEntity(
            SingleTripQueryResult singleTripQueryResult, Vehicle vehicle)
    {
        // TODO Auto-generated method stub
        // only dummy implementation
        singleTripQueryResult.getVehicle().setManufactor("BMW");
    }

    private Timestamp toTimestamp(Date startDateTime)
    {
        return new Timestamp(startDateTime.getTime());
    }

    /**
     * 
     * @param message
     * @return
     */
    public TripQueryNoResults newNoTripsResult(String message)
    {
        TripQueryNoResults noResults = new TripQueryNoResults();
        
        noResults.setNoTrips(true);
        noResults.setMessage(message);
        
        return noResults;
    }

    public TripQueryResults newTripQueryResults(
            List<SingleTripQueryResult> list)
    {
        TripQueryResults results = new TripQueryResults();
        addTranslations(results);
        results.setTrips(list);
        return results;
    }

    private void addTranslations(TripQueryResults results)
    {
        Translations translations = results.getTranslations();
        
        translations.setDestinationDistance(translator.translate(translations.getDestinationDistance()));
        translations.setDriver(translator.translate(translations.getDriver()));
        translations.setLocationDistance(translator.translate(translations.getLocationDistance()));
        translations.setNumberBookings(translator.translate(translations.getNumberBookings()));
        translations.setOverallPrice(translator.translate(translations.getOverallPrice()));
        translations.setStartDate(translator.translate(translations.getStartDate()));
        translations.setTo(translator.translate(translations.getTo()));
        translations.setVehicle(translator.translate(translations.getVehicle()));        
        translations.setBook(translator.translate(translations.getBook()));
        translations.setBookTooltip(translator.translate(translations.getBookTooltip()));
    }
    
    /**
     * Reconstruct a {@link TripSearchCriteria} by a given {@link Booking} instance, e.g. for hash checking.
     * @param booking
     * @return
     */
    public TripSearchCriteria newTripSearchCriteriaBy(Booking booking)
    {
        TripSearchCriteria searchCriteria = new TripSearchCriteria();
        
        searchCriteria.setStartPoint(booking.getStartPoint());
        searchCriteria.setEndPoint(booking.getEndPoint());
        searchCriteria.setLatStartPoint(booking.getStartLatitude());
        searchCriteria.setLongStartPoint(booking.getStartLongitude());
        searchCriteria.setLatEndPoint(booking.getEndLatitude());
        searchCriteria.setLongEndPoint(booking.getEndLongitude());
        
        return searchCriteria;
    }

}
