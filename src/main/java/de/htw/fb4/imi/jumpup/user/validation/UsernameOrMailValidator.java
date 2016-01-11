/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.user.validation;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import de.htw.fb4.imi.jumpup.settings.BeanNames;
import de.htw.fb4.imi.jumpup.validation.validator.JumpUpValidator;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 27.11.2014
 *
 */
@Named(value=BeanNames.USERNAME_OR_MAIL_VALIDATOR)
@RequestScoped
public class UsernameOrMailValidator extends AbstractUserValidator
{
    protected JumpUpValidator eMailValidator;
    protected JumpUpValidator userValidator;
    
   

    protected void initializeIfNecessary()
    {
        if (null == this.eMailValidator || null == this.userValidator) {
            // disable DB checks - only check eMail and username format in general
            this.eMailValidator = new EMail();
            ((EMail) this.eMailValidator).enableDBCheck(false);
            ((EMail) this.eMailValidator).setConfigReader(this.userConfigReader);
            this.userValidator = new Username();
            ((Username) this.userValidator).enableDBCheck(false);
            ((Username) this.userValidator).setConfigReader(this.userConfigReader);
        }
    }

    @Override
    /* (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.util.JumpUpValidator#validate(java.lang.Object)
     */
    public boolean validate(final Object value)
    {
        this.initializeIfNecessary();
        
        final String usernameOrMail = (String) value;
        
        if (!this.eMailValidator.validate(usernameOrMail) &&
            !this.userValidator.validate(usernameOrMail)) {
            return false;
        }
        
        return true;
    }

    @Override
    /*
     * 
     */
    public int getMinLength()
    {
        this.initializeIfNecessary();
        
        if (this.eMailValidator.getMinLength() <= this.userValidator.getMinLength()) {
            return this.eMailValidator.getMinLength();
        }
        
        return this.userValidator.getMinLength();
    }

    @Override
    public int getMaxLength()
    {
        this.initializeIfNecessary();
        
        if (this.eMailValidator.getMaxLength() >= this.userValidator.getMaxLength()) {
            return this.eMailValidator.getMaxLength();
        }
        
        return this.userValidator.getMaxLength();
    }
    
    /*
     * (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.validator.AbstractValidator#getDefaultFailureMessage()
     */
    protected String getDefaultFailureMessage()
    {
        return "Please type in a correct username or eMail.";
    }
}
