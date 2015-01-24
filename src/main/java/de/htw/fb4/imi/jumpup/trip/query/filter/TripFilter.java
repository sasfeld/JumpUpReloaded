/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.trip.query.filter;

import java.util.List;

import javax.ejb.Local;

import de.htw.fb4.imi.jumpup.trip.entities.Trip;
import de.htw.fb4.imi.jumpup.trip.query.TripSearchCriteria;

/**
 * <p>Filter {@link Trip} entities.</p>
 * 
 * <p>Filters follow the decorator pattern and implement this filter method.</p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 22.01.2015
 *
 */
@Local
public interface TripFilter
{
    /**
     * Set criteria that can be applied by this trip filter.
     * @param tripSearchCriteria
     */
    void setCriteria(TripSearchCriteria tripSearchCriteria);
    
    /**
     * Apply the filter function on the given list of trips.
     * @param givenTrips
     * @return
     */
    List<Trip> applyFilter(final List<Trip> givenTrips);    
}