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
@Named( value = BeanNames.LASTNAME_VALIDATOR)
@RequestScoped
public class Lastname extends AbstractUserValidator
{   
    public static String PATTERN_LASTNAME = "[a-zA-Z- ]+";


    @Override
    /* (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.util.JumpUpValidator#validate(java.lang.Object)
     */
    public boolean validate(final Object value)
    {
        final String lastname = ((String) value).trim();
        
        if (!this.checkLength(lastname)
                || !this.checkCharacters(lastname)) {
                return false;
            }
        
        return true;
    }

    /**
     * Check length from user.properties config.
     * @param lastname
     * @return
     */
    private boolean checkCharacters(String lastname)
    {
        if (!lastname.matches(PATTERN_LASTNAME)) {
            return false;
        }
        
        return true;
    }

    protected boolean checkLength(String lastname)
    {
     // get values from user.properties configuration file
        int minLength = this.getMinLength();
        int maxLength = this.getMaxLength();
        
        if (lastname.length() < minLength) {
            this.errorMessages.add("The lastname you entered is too short. Please type in at least " + minLength + " characters.");
            
            return false;
        } else if (lastname.length() > maxLength) {
            this.errorMessages.add("The lastname you entered is too long. Please type in at maximum " + maxLength + " characters.");
            
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
        return  Integer.parseInt(userConfigReader.fetchValue(IConfigKeys.JUMPUP_USER_VALIDATION_LASTNAME_MIN_LENGTH));
    }

    @Override
    /*
     * (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.validator.JumpUpValidator#getMaxLength()
     */
    public int getMaxLength()
    {
        return Integer.parseInt(userConfigReader.fetchValue(IConfigKeys.JUMPUP_USER_VALIDATION_LASTNAME_MAX_LENGTH));
    }
    
    /*
     * (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.validator.AbstractValidator#getDefaultFailureMessage()
     */
    protected String getDefaultFailureMessage()
    {
        return "The lastname you entered is not valid.";
    }

}
