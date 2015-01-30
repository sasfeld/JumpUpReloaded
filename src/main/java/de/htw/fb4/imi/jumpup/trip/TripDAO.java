/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.trip;

import javax.ejb.Local;

import de.htw.fb4.imi.jumpup.trip.entities.Trip;

/**
 * <p>Data access object to perform database operations on {@link Trip} entities.</p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 30.01.2015
 *
 */
@Local
public interface TripDAO
{    
    /**
     * Load the given trip by ID.
     * 
     * @param id
     * @return
     */
    Trip getTripByID(long identity);

}
