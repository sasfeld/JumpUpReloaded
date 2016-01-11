/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.user.validation;



import javax.enterprise.context.RequestScoped;
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
@Named(value=BeanNames.PRENAME_VALIDATOR)
@RequestScoped
public class Prename extends AbstractUserValidator
{   
    public static String PATTERN_PRENAME = "[a-zA-Z- ]+";


    @Override
    /* (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.util.JumpUpValidator#validate(java.lang.Object)
     */
    public boolean validate(final Object value)
    {
        final String prename = ((String) value).trim();
        
        if (!this.checkLength(prename)
                || !this.checkCharacters(prename)) {
                return false;
            }
        
        return true;
    }

    /**
     * Check length from user.properties config.
     * @param prename
     * @return
     */
    private boolean checkCharacters(String prename)
    {
        if (!prename.matches(PATTERN_PRENAME)) {
            return false;
        }
        
        return true;
    }

    protected boolean checkLength(String prename)
    {
        // get values from user.properties configuration file
        int minLength = this.getMinLength();
        int maxLength = this.getMaxLength();
        
        if (prename.length() < minLength) {
            this.errorMessages.add("The prename you entered is too short. Please type in at least " + minLength + " characters.");
            
            return false;
        } else if (prename.length() > maxLength) {
            this.errorMessages.add("The prename you entered is too long. Please type in maximum " + maxLength + " characters.");
            
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
        return Integer.parseInt(userConfigReader.fetchValue(IConfigKeys.JUMPUP_USER_VALIDATION_PRENAME_MIN_LENGTH));
    }

    @Override
    /*
     * (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.validator.JumpUpValidator#getMaxLength()
     */
    public int getMaxLength()
    {
        return Integer.parseInt(userConfigReader.fetchValue(IConfigKeys.JUMPUP_USER_VALIDATION_PRENAME_MAX_LENGTH));
    }

    /*
     * (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.validator.AbstractValidator#getDefaultFailureMessage()
     */
    protected String getDefaultFailureMessage()
    {
        return "The prename you entered is not valid.";
    }
}
