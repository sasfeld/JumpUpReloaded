/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.user.validation;



import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

import de.htw.fb4.imi.jumpup.config.IConfigKeys;
import de.htw.fb4.imi.jumpup.settings.BeanNames;
import de.htw.fb4.imi.jumpup.validator.AbstractValidator;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 24.11.2014
 *
 */
@Named( value = BeanNames.PASSWORD_VALIDATOR )
@RequestScoped
public class Password extends AbstractValidator
{   
    public static String PATTERN_NUMERICAL = ".*[0-9]+.*";
    public static String PATTERN_ALPHA = ".*[a-zA-Z]+.*";
    public static String PATTERN_SPECIAL_CHARACTER = ".*[^a-zA-Z0-9]+.*";

    @Override    
    /* (non-Javadoc)
     * @see javax.faces.validator.Validator#validate(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
     */
    public void validate(final FacesContext context, final UIComponent component,
            final Object value) throws ValidatorException
    {
        // throw validator with invalid entry message per default if validate() returns false
        if (!this.validate(value)) {
            // get first error message or print default
            String msg = "The password you entered does not follow our security guidelines.";
            if (this.errorMessages.size() > 0) {
                msg = (String) this.errorMessages.toArray()[0];
            }
            
            throw new ValidatorException(this.facesFacade.newValidationErrorMessage(msg, 
                    msg));
        }
    }

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
        int minLength = Integer.parseInt(userConfigReader.fetchValue(IConfigKeys.JUMUP_USER_VALIDATION_PASSWORD_MIN_LENGTH));
        int maxLength = Integer.parseInt(userConfigReader.fetchValue(IConfigKeys.JUMUP_USER_VALIDATION_PASSWORD_MAX_LENGTH));
        
        if (password.length() < minLength) {
            this.errorMessages.add("The password you entered is too short. Please type in at least " + minLength + " characters.");
            
            return false;
        } else if (password.length() > maxLength) {
            this.errorMessages.add("The password you entered is too long. Please type in maximum " + maxLength + " characters.");
            
            return false;
        }
        
        return true;
    }

}
