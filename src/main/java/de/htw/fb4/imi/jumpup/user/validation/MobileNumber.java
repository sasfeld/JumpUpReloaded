/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.user.validation;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import de.htw.fb4.imi.jumpup.settings.BeanNames;
import de.htw.fb4.imi.jumpup.validator.AbstractValidator;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 14.12.2014
 *
 */
@Named(value = BeanNames.MOBILE_NUMBER_VALIDATOR)
@RequestScoped
public class MobileNumber extends AbstractValidator
{
    private static final String REGEX_MOBILE_NUMBER = "^\\+[0-9]{1,3}[0-9 ]+";
    

    @Override
    /* (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.util.JumpUpValidator#validate(java.lang.Object)
     */
    public boolean validate(final Object value)
    {
        final String mobileNumber = ((String) value).trim();
        
        if (!this.checkLength(mobileNumber)
                || !this.checkPattern(mobileNumber)) {
                return false;
            }
        
        return true;
    }
    
    /**
     * 
     * @param mobileNumber
     * @return
     */
    private boolean checkPattern(String mobileNumber)
    {
        if (mobileNumber.matches(REGEX_MOBILE_NUMBER)) {
            return true;
        }
        
        // else: doesn't match        
        this.errorMessages.add("The mobile number must have the standard format, e.g. +49 176 9999999");
        return false;
    }

    /**
     * 
     * @param mobileNumber
     * @return
     */
    protected boolean checkLength(String mobileNumber)
    {        
        if (mobileNumber.length() < this.getMinLength() || mobileNumber.length() > this.getMaxLength()) {
            return false;
        }
        
        return true;
    }

    /* (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.validator.JumpUpValidator#getMinLength()
     */
    @Override
    public int getMinLength()
    {
        return 7;
    }

    /* (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.validator.JumpUpValidator#getMaxLength()
     */
    @Override
    public int getMaxLength()
    {
        return 100;
    }
    
    /*
     * (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.validator.AbstractValidator#getDefaultFailureMessage()
     */
    protected String getDefaultFailureMessage()
    {
        return "You entered an invalid mobile number.";
    }
}
