/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.user.validation;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.servlet.http.Part;

import de.htw.fb4.imi.jumpup.config.IConfigKeys;
import de.htw.fb4.imi.jumpup.settings.BeanNames;
import de.htw.fb4.imi.jumpup.validator.AbstractValidator;

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
public class AvatarFile extends AbstractValidator
{
    private static final String CONTENT_TYPE_PNG = "image/png";
    private static final String CONTENT_TYPE_JPG = "image/jpeg";

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
            String msg = "Your avatar file is not valid.";
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
            return false;
        }
        return true;
    }

    private boolean checkLength(Part avatarFile)
    {
        if (avatarFile.getSize() > getMaxLength() 
                || avatarFile.getSize() < getMinLength()) {
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

}
