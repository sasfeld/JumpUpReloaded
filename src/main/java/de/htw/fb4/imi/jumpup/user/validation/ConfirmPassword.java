/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.user.validation;



import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

import de.htw.fb4.imi.jumpup.Application;
import de.htw.fb4.imi.jumpup.Application.LogType;
import de.htw.fb4.imi.jumpup.config.IConfigKeys;
import de.htw.fb4.imi.jumpup.settings.BeanNames;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 24.11.2014
 *
 */
@Named(value=BeanNames.REPEAT_PASSWORD_VALIDATOR)
@RequestScoped
public class ConfirmPassword extends AbstractUserValidator
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
        UIInput password = (UIInput) context.getViewRoot().findComponent("registrationForm:password");        
        
        if (null == password) {
            Application.log("Nullpointer in validate(): could not get the value of the first password field. Please check what is happening.", LogType.CRITICAL, getClass());
            
            // TODO check the bug, why is firstPassword null?
            return;
        }
        
        String[] compareValues = { (String) value, (String) password.getSubmittedValue() };
        // throw validator with invalid entry message per default if validate() returns false
        if (!this.validate(compareValues)) {            
            throw new ValidatorException(this.facesFacade.newValidationErrorMessage("The passwords don't match.", 
                    this.getDefaultFailureMessage()));
        }
    }

    @Override
    /* (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.util.JumpUpValidator#validate(java.lang.Object)
     */
    public boolean validate(final Object value)
    {
        final String firstPassword = ((String[]) value)[1];
        final String repeatPassword = ((String[]) value)[0];
        
        if (null == firstPassword) {
            Application.log("Nullpointer in validate(): could not get the value of the first password field. Please check what is happening.", LogType.CRITICAL, getClass());
            
            // TODO check the bug, why is firstPassword null?
            return true;
        }
        
        if (!firstPassword.trim().equals(repeatPassword.trim())) {
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

    @Override
    protected String getDefaultFailureMessage()
    {
        return "The passwords you entered don't match. Please type in again.";
    }
}
