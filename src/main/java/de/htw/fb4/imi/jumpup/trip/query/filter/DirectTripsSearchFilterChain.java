/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.trip.query.filter;

import javax.ejb.Stateless;

import de.htw.fb4.imi.jumpup.settings.BeanNames;

/**
 * <p>This EJB bean contains the filter chain that is performed when a passenger is searching by criteria for trips.</p>
 * 
 * <p>Just inject it and call setCriteria() and applyFilter() to enable the chain.</p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 22.01.2015
 *
 */
@Stateless(name = BeanNames.TRIP_SEARCH_FILTER_CHAIN)
public class DirectTripsSearchFilterChain extends AbstractTripFilter
{
    
    public DirectTripsSearchFilterChain()
    {
        super(new NearbyTripsFilter());
    }
}
