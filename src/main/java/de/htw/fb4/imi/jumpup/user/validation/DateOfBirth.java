/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.user.validation;

import java.util.Date;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import de.htw.fb4.imi.jumpup.Application;
import de.htw.fb4.imi.jumpup.Application.LogType;
import de.htw.fb4.imi.jumpup.config.IConfigKeys;
import de.htw.fb4.imi.jumpup.settings.BeanNames;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 14.12.2014
 *
 */
@Named(value=BeanNames.DATE_OF_BIRTH)
@RequestScoped
public class DateOfBirth extends AbstractUserValidator
{    
    /*
     * (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.validator.AbstractValidator#validate(java.lang.Object)
     */
    public boolean validate(final Object value)
    {
        if (!(value instanceof Date)) {
            Application.log("validate(): given value is not of type date", LogType.ERROR, getClass());
            return false;
        }
        
        Date dob = (Date) value;
        
        if (!this.userHasMinimumAge(dob)) {
           return false;
        }
        
        return true;
    }

    /**
     * Check whether the user has reached the minimum age.
     * @param dob 
     * @return
     */
    private boolean userHasMinimumAge(final Date dob)
    {
        int minimumAge = getConfiguredAge();
        
        Date current = new Date();        
        
        long difference = current.getTime() - dob.getTime();

        // check if difference is below configured years in milliseconds
        long eighteenYears = 1000 * 60 * 60 * 24 * 365 * minimumAge;
        if (difference < eighteenYears) {
            return false;
        }
        
        return true;
    }

    protected int getConfiguredAge()
    {
        int minimumAge = Integer.parseInt(this.userConfigReader.fetchValue(IConfigKeys.JUMPUP_USER_MINIMUM_AGE));
        return minimumAge;
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
        return 10;
    }
    
    /*
     * (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.validator.AbstractValidator#getDefaultFailureMessage()
     */
    protected String getDefaultFailureMessage()
    {
        return "You must be at least " + this.getConfiguredAge() + " years old.";
    }

}
