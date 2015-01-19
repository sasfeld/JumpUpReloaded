/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.user.validation;



import javax.faces.bean.RequestScoped;
import javax.inject.Named;

import de.htw.fb4.imi.jumpup.config.IConfigKeys;
import de.htw.fb4.imi.jumpup.settings.BeanNames;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 24.11.2014
 *
 */
@Named( value = BeanNames.PASSWORD_VALIDATOR )
@RequestScoped
public class Password extends AbstractUserValidator
{   
    public static String PATTERN_NUMERICAL = ".*[0-9]+.*";
    public static String PATTERN_ALPHA = ".*[a-zA-Z]+.*";
    public static String PATTERN_SPECIAL_CHARACTER = ".*[^a-zA-Z0-9]+.*";
    

    @Override
    /* (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.util.JumpUpValidator#validate(java.lang.Object)
     */
    public boolean validate(final Object value)
    {
        final String password = ((String) value).trim();
        
        if (!this.checkLength(password)
            || !this.checkSpecialCharacters(password)) {
            return false;
        }
        
        return true;
    }

    /**
     * Check for special characters.
     * @param password
     * @return
     */
    protected boolean checkSpecialCharacters(final String password)
    {
        if (!password.matches(PATTERN_NUMERICAL)) {
            this.errorMessages.add("The password needs at least one number.");   
            
            return false;
        } else if (!password.matches(PATTERN_ALPHA)) {
            this.errorMessages.add("The password must contain at least one alphanumerical character.");
            
            return false;
        } else if (!password.matches(PATTERN_SPECIAL_CHARACTER)) {
            this.errorMessages.add("The password must contain at least one special character.");
            
            return false;
        }
        
        return true;
    }

    /** 
     * Check minimum and maximum lengths.
     * @param password
     * @return
     */
    protected boolean checkLength(final String password)
    {
        // get values from user.properties configuration file
        int minLength = this.getMinLength();
        int maxLength = this.getMaxLength();
        
        if (password.length() < minLength) {
            this.errorMessages.add("The password you entered is too short. Please type in at least " + minLength + " characters.");
            
            return false;
        } else if (password.length() > maxLength) {
            this.errorMessages.add("The password you entered is too long. Please type in maximum " + maxLength + " characters.");
            
            return false;
        }
        
        return true;
    }

    @Override
    /*
     * (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.validator.JumpUpValidator#getMinLength()
     */
    public int getMinLength()
    {
        return Integer.parseInt(userConfigReader.fetchValue(IConfigKeys.JUMPUP_USER_VALIDATION_PASSWORD_MIN_LENGTH));
    }

    @Override
    /*
     * (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.validator.JumpUpValidator#getMaxLength()
     */
    public int getMaxLength()
    {
        return Integer.parseInt(userConfigReader.fetchValue(IConfigKeys.JUMPUP_USER_VALIDATION_PASSWORD_MAX_LENGTH));
    }
    
    /*
     * (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.validator.AbstractValidator#getDefaultFailureMessage()
     */
    protected String getDefaultFailureMessage()
    {
        return "The password you entered does not follow our security guidelines.";
    }
}
