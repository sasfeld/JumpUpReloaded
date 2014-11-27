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

import de.htw.fb4.imi.jumpup.settings.BeanNames;
import de.htw.fb4.imi.jumpup.validator.AbstractValidator;
import de.htw.fb4.imi.jumpup.validator.JumpUpValidator;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 27.11.2014
 *
 */
@Named( value = BeanNames.UsernameOrMailValidator )
@RequestScoped
public class UsernameOrMailValidator extends AbstractValidator
{
    protected JumpUpValidator eMailValidator;
    protected JumpUpValidator userValidator;
    
    public UsernameOrMailValidator()
    {
        initialize();        
    }

    protected void initialize()
    {
        // disable DB checks - only check eMail and username format in general
        this.eMailValidator = new EMail();
        ((EMail) this.eMailValidator).enableDBCheck(false);
        this.userValidator = new Username();
        ((Username) this.userValidator).enableDBCheck(false);
    }
    
    /* (non-Javadoc)
     * @see javax.faces.validator.Validator#validate(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
     */
    public void validate(final FacesContext context, final UIComponent component,
            final Object value) throws ValidatorException
    {
        // throw validator with invalid entry message per default if validate() returns false
        if (!this.validate(value)) {
            throw new ValidatorException(this.facesFacade.newValidationErrorMessage("Invalid username or eMail.", 
                    "Please type in a correct username or eMail."));
        }
    }

    @Override
    /* (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.util.JumpUpValidator#validate(java.lang.Object)
     */
    public boolean validate(final Object value)
    {
        final String usernameOrMail = (String) value;
        
        if (!this.eMailValidator.validate(usernameOrMail) &&
            !this.userValidator.validate(usernameOrMail)) {
            return false;
        }
        
        return true;
    }

}
