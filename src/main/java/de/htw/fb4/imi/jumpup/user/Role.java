/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.user;

import de.htw.fb4.imi.jumpup.booking.entities.Booking;
import de.htw.fb4.imi.jumpup.user.entities.User;

/**
 * <p>Enumeration to characterize a {@link User}'s role in different situations, such as booking a trip (-> passenger) or offering a trip (-> driver).</p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 04.02.2015
 *
 */
public enum Role
{
    /**
     * {@link User} acts as driver, offering trips, cancelling or confirming {@link Booking} etc.
     */
    DRIVER,
    /**
     * {@link User} acts as passenger, creating {@link Booking} etc.
     */
    PASSENGER    
}
