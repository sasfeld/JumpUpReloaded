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
 * @since 14.12.2014
 *
 */
@Named(value=BeanNames.PLACE_OF_BIRTH_VALIDATOR)
@RequestScoped
public class PlaceOfBirth extends AbstractUserValidator
{
    private static final String PATTERN_PLACE_OF_BIRTH = "[A-Za-z\\- ]+";


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
    
    /*
     * (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.validator.AbstractValidator#getDefaultFailureMessage()
     */
    protected String getDefaultFailureMessage()
    {
        return "You entered an invalid place of birth.";
    }
}
