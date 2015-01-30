/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.trip.validation;

import javax.inject.Named;

import de.htw.fb4.imi.jumpup.settings.BeanNames;
import de.htw.fb4.imi.jumpup.trip.util.TripAndBookingsConfigKeys;

/**
 * <p>Validator for location strings, e.g. 'Berlin'.</p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 17.01.2015
 *
 */
@Named(value = BeanNames.LOCATION_VALIDATOR)
public class LocationValidator extends AbstractTripValidator
{
    private static final String PATTERN_LOCATION = "^[A-Za-z0-9-., äöüß]+$";

    @Override
    /* (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.util.JumpUpValidator#validate(java.lang.Object)
     */
    public boolean validate(final Object value)
    {
        final String location = ((String) value).trim();
        
        if (!this.checkLength(location)
                || !this.checkPattern(location)) {
                return false;
            }
        
        return true;
    }
    
    private boolean checkPattern(String location)
    {
       if (location.matches(PATTERN_LOCATION)) {
           return true;
       }
       
       return false;
    }

    /* (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.validator.JumpUpValidator#getMinLength()
     */
    @Override
    public int getMinLength()
    {
        return Integer.parseInt(this.tripConfigReader.fetchValue(TripAndBookingsConfigKeys.JUMPUP_TRIP_VALIDATION_LOCATION_MIN_LENGTH));
    }

    /* (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.validator.JumpUpValidator#getMaxLength()
     */
    @Override
    public int getMaxLength()
    {
        return Integer.parseInt(this.tripConfigReader.fetchValue(TripAndBookingsConfigKeys.JUMPUP_TRIP_VALIDATION_LOCATION_MAX_LENGTH));
    }

    @Override
    /*
     * (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.validator.AbstractValidator#getDefaultFailureMessage()
     */
    protected String getDefaultFailureMessage()
    {
        return "You entered an invalid location.";
    }
}
