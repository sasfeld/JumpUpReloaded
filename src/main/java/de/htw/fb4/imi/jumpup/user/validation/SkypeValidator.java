/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.user.validation;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import de.htw.fb4.imi.jumpup.settings.BeanNames;
import de.htw.fb4.imi.jumpup.validation.validator.AbstractValidator;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 14.12.2014
 *
 */
@Named(value = BeanNames.SKYPE_VALIDATOR)
@RequestScoped
public class SkypeValidator extends AbstractValidator
{
    private static final String PATTERN_SYKPE = "[a-zA-Z][a-zA-Z0-9\\.,\\-_]*";


    @Override
    /* (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.util.JumpUpValidator#validate(java.lang.Object)
     */
    public boolean validate(final Object value)
    {
        final String skype = ((String) value).trim();
        
        if (!this.checkLength(skype)
                || !this.checkPattern(skype)) {
                return false;
            }
        
        return true;
    }

    private boolean checkPattern(String skype)
    {
        if (!skype.matches(PATTERN_SYKPE)) {
            this.errorMessages.add("Please type in a valid skype account name.");
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
        return 0;
    }

    /* (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.validator.JumpUpValidator#getMaxLength()
     */
    @Override
    public int getMaxLength()
    {
        return 31;
    }
    
    /*
     * (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.validator.AbstractValidator#getDefaultFailureMessage()
     */
    protected String getDefaultFailureMessage()
    {
        return "You entered an invalid skype account name.";
    }
}
