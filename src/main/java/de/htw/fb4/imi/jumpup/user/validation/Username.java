/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.user.validation;



import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import de.htw.fb4.imi.jumpup.user.entities.User;
import de.htw.fb4.imi.jumpup.util.Faces;
import de.htw.fb4.imi.jumpup.validator.AbstractValidator;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 24.11.2014
 *
 */
public class Username extends AbstractValidator
{

    @Override    
    /* (non-Javadoc)
     * @see javax.faces.validator.Validator#validate(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
     */
    public void validate(final FacesContext context, final UIComponent component,
            final Object value) throws ValidatorException
    {
        // throw validator with invalid entry message per default if validate() returns false
        if (!this.validate(value)) {
            throw new ValidatorException(Faces.newValidationErrorMessage("The username already exists.", 
                    "The username was already taken by another registered user. Please try another one."));
        }
    }

    @Override
    /* (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.util.JumpUpValidator#validate(java.lang.Object)
     */
    public boolean validate(final Object value)
    {
        final String username = (String) value;
        
        // check whether more than user already exists.
        if ( 0 < this.entityManager.createNamedQuery(User.NAME_QUERY_BY_USERNAME).
                setParameter("username", username).getResultList().size()) {
            return false;
        }
        
        return true;
    }

}
