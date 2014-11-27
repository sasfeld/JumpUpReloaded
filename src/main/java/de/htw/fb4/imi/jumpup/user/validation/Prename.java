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
@Named( value = BeanNames.PRENAME_VALIDATOR)
@RequestScoped
public class Prename extends AbstractValidator
{   
    public static String PATTERN_PRENAME = "[a-zA-Z- ]+";

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
            String msg = "The prename you entered is not valid.";
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

    private boolean checkLength(String prename)
    {
        // get values from user.properties configuration file
        int minLength = Integer.parseInt(userConfigReader.fetchValue(IConfigKeys.JUMUP_USER_VALIDATION_PRENAME_MIN_LENGTH));
        int maxLength = Integer.parseInt(userConfigReader.fetchValue(IConfigKeys.JUMUP_USER_VALIDATION_PRENAME_MAX_LENGTH));
        
        if (prename.length() < minLength) {
            this.errorMessages.add("The prename you entered is too short. Please type in at least " + minLength + " characters.");
            
            return false;
        } else if (prename.length() > maxLength) {
            this.errorMessages.add("The prename you entered is too long. Please type in maximum " + maxLength + " characters.");
            
            return false;
        }
        
        return true;
    }

}
