/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.user.validation;

import javax.enterprise.context.RequestScoped;
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
 * @since 14.12.2014
 *
 */
@Named(value = BeanNames.PLACE_OF_BIRTH_VALIDATOR)
@RequestScoped
public class PlaceOfBirth extends AbstractValidator
{
    private static final String PATTERN_PLACE_OF_BIRTH = "[A-Z- ]+";

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
            String msg = "You entered an invalid place of birth.";
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
        final String placeOfBirth = ((String) value).trim();
        
        if (!this.checkLength(placeOfBirth)
                || !this.checkPattern(placeOfBirth)) {
                return false;
            }
        
        return true;
    }
    
    private boolean checkPattern(String placeOfBirth)
    {
        if (placeOfBirth.matches(PATTERN_PLACE_OF_BIRTH))
        {
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
        return Integer.parseInt(this.userConfigReader.fetchValue(IConfigKeys.JUMPUP_USER_PLACE_OF_BIRTH_MIN_LENGTH));
    }

    /* (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.validator.JumpUpValidator#getMaxLength()
     */
    @Override
    public int getMaxLength()
    {
        return Integer.parseInt(this.userConfigReader.fetchValue(IConfigKeys.JUMPUP_USER_PLACE_OF_BIRTH_MAX_LENGTH));
    }

}
