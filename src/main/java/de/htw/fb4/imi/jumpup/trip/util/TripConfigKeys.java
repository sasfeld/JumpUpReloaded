/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.trip.util;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 17.01.2015
 *
 */
public interface TripConfigKeys
{
    String JUMPUP_TRIP_VALIDATION_LOCATION_MIN_LENGTH = "jumpup.trip.validation.location.min_length";
    String JUMPUP_TRIP_VALIDATION_LOCATION_MAX_LENGTH = "jumpup.trip.validation.location.max_length";
    
    String JUMPUP_TRIP_VALIDATION_START_END_DATETIME_MIN_HOURS_IN_FUTURE = "jumpup.trip.validation.start_end_datetime.min_hours_in_future";
    
    String JUMPUP_TRIP_VALIDATION_PRICE_MIN_VALUE_IN_EURO = "jumpup.trip.validation.price.min_value_in_euro";
    String JUMPUP_TRIP_VALIDATION_PRICE_MAX_VALUE_IN_EURO = "jumpup.trip.validation.price.max_value_in_euro";
}
