/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.user.validation;

import javax.inject.Named;
import javax.servlet.http.Part;

import de.htw.fb4.imi.jumpup.config.IConfigKeys;
import de.htw.fb4.imi.jumpup.settings.BeanNames;

/**
 * <p>Validator for the user's avatar file.</p>
 * 
 * <p>The file will be uploaded via file upload.</p> 
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 08.01.2015
 *
 */
@Named(value = BeanNames.AVATAR_FILE_VALIDATOR)
public class AvatarFile extends AbstractUserValidator
{
    private static final String CONTENT_TYPE_PNG = "image/png";
    private static final String CONTENT_TYPE_JPG = "image/jpeg";

    

    @Override
    /* (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.util.JumpUpValidator#validate(java.lang.Object)
     */
    public boolean validate(final Object value)
    {
        final Part avatarFile = (Part) value;
        
        if (!this.checkLength(avatarFile)
                || !this.checkType(avatarFile)) {
                return false;
            }
        
        return true;
    }

    private boolean checkType(final Part avatarFile)
    {
        if (!avatarFile.getContentType().equals(CONTENT_TYPE_PNG)
                || !avatarFile.getContentType().equals(CONTENT_TYPE_JPG)
           ) {
            this.errorMessages.add("The avatar file must be of type png or jpg.");
            return false;
        }
        return true;
    }

    private boolean checkLength(Part avatarFile)
    {
        if (avatarFile.getSize() > getMaxLength() 
                || avatarFile.getSize() < getMinLength()) {
            this.errorMessages.add("The avatar file musn't have more than " + getMaxLength() + " bytes.");
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
        return Integer.parseInt(this.userConfigReader.fetchValue(IConfigKeys.JUMPUP_USER_AVATAR_MAX_SIZE));
    }

    @Override
    /*
     * (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.validator.AbstractValidator#getDefaultFailureMessage()
     */
    protected String getDefaultFailureMessage()
    {
        return "Your avatar file is not valid.";
    }
}
