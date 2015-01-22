/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.trip.query.filter;

import java.util.List;

import de.htw.fb4.imi.jumpup.trip.entities.Trip;
import de.htw.fb4.imi.jumpup.trip.query.TripSearchCriteria;

/**
 * <p>This filter is the most important one. <br />
 * It is responsible for identifying nearby {@link Trip} that are located next to passengers start and end location.
 * </p>
 * 
 * <p>Therefore, check if the given latitudes and longitudes are near some of the waypoints of one {@link Trip} instance.<br />
 * The property overviewPath contains most of the waypoints as comma-separated list of lat/lng values.</p>
 * 
 * <p>The criteria is given by a {@link TripSearchCriteria} query model. The maximum distance to the {@link Trip} waypoints at both, passenger's start and end location must not be greater than "maximumDistance".</p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 22.01.2015
 *
 */
public class NearbyTripsFilter extends AbstractTripFilter
{

    /* (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.trip.query.filter.TripFilter#applyFilter(java.util.List)
     */
    @Override
    public List<Trip> applyFilter(final List<Trip> givenTrips)
    {
        // TODO implement
        return super.applyFilter(givenTrips);
    }
}
