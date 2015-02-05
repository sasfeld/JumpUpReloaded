/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.trip.query;

import java.util.List;

import javax.ejb.Local;

import de.htw.fb4.imi.jumpup.trip.entities.Trip;
import de.htw.fb4.imi.jumpup.trip.restservice.model.TripQueryResults;
import de.htw.fb4.imi.jumpup.trip.restservice.model.TripSearchCriteria;
import de.htw.fb4.imi.jumpup.user.entities.User;
import de.htw.fb4.imi.jumpup.util.ErrorPrintable;

/**
 * <p>This EJB offers services to:</p> 
 * <ul>
 *  <li>Fetching trips that a driver offers</li>
 *  <li>TODO Fetching trips that a passenger booked</li>
 *  <li>TODO A passenger searchs for trips matching special criteria</li>
 * </ul>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 19.01.2015
 *
 */
@Local
public interface TripQueryMethod extends ErrorPrintable
{
    
    /**
     * Get trips that the given {@link User} offered as a driver.
     * @param user
     * @return
     */
    List<Trip> getOfferedTrips(final User user);    
    
    /**
     * Get trips that match the criteria as given by the {@link TripSearchCriteria} instance.
     * 
     * @param tripSearchModel
     * @return a list of {@link Trip} which can be empty if the criteria was not met
     */
    TripQueryResults searchForTrips(final TripSearchCriteria tripSearchModel);

    /**
     * Build and reutrn TripQueryNoResult.
     * @return
     */
    TripQueryNoResults getNoTripsResult();

}
