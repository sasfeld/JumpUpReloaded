/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.trip.validation;

import java.util.Date;

import javax.inject.Named;

import de.htw.fb4.imi.jumpup.Application;
import de.htw.fb4.imi.jumpup.Application.LogType;
import de.htw.fb4.imi.jumpup.settings.BeanNames;
import de.htw.fb4.imi.jumpup.trip.util.TripAndBookingsConfigKeys;

/**
 * <p>Validator for datetimes.</p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 17.01.2015
 *
 * TODO check that endDateTime > startDateTime
 */
@Named(value = BeanNames.END_DATETIME_VALIDATOR)
public class EndDatetimeValidator extends AbstractTripValidator
{

    @Override
    /* (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.util.JumpUpValidator#validate(java.lang.Object)
     */
    public boolean validate(final Object value)
    {
        final Date date = castToDate(value);
        
        if (!this.isInConfiguredFuture(date)) {
                return false;
            }
        
        return true;
    }
    

    private Date castToDate(Object value)
    {
        if (value instanceof Date) {
            return (Date) value;
        }
        
        Application.log("castToDate(): could not cast " + value + " to datetime, current type of it: " + value.getClass(), LogType.ERROR, getClass());
        this.errorMessages.add("The datetime could not be converted.");
        return null;
    }
    
    /**
     * Check if the trip is configured to take place an appropriate time in the future.
     * @param value
     * @return
     */
    protected boolean isInConfiguredFuture(Date value)
    {
        int minNumberHours = Integer.parseInt(this.tripConfigReader.fetchValue(TripAndBookingsConfigKeys.JUMPUP_TRIP_VALIDATION_START_END_DATETIME_MIN_HOURS_IN_FUTURE));
        
        long currentTimestamp = new Date().getTime();
        long configuredTimestamp = value.getTime();
        
        // if end date is back in past or less than configured hours from today one
        if ((configuredTimestamp - currentTimestamp) < 0 
                || (configuredTimestamp - currentTimestamp) < minNumberHours * 1000 * 60 * 60 ) {
            this.errorMessages.add("The trip must take place at least " + minNumberHours + " hours in the future");
            return false;
        }
        
        return true;       
    }


    /* (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.validator.JumpUpValidator#getMinLength()
     */
    @Override
    public int getMinLength()
    {
        return 0;
    }

    /* (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.validator.JumpUpValidator#getMaxLength()
     */
    @Override
    public int getMaxLength()
    {
        return 0;
    }

    @Override
    /*
     * (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.validator.AbstractValidator#getDefaultFailureMessage()
     */
    protected String getDefaultFailureMessage()
    {
        return "You entered an invalid end datetime.";
    }
}
