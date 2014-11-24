/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.validator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import de.htw.fb4.imi.jumpup.settings.PersistenceSettings;
import de.htw.fb4.imi.jumpup.util.Faces;

/**
 * <p>Abstract class for JSF validation classes.</p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 24.11.2014
 *
 */
public abstract class AbstractValidator implements Validator, JumpUpValidator
{
    @PersistenceUnit(unitName=PersistenceSettings.PERSISTENCE_UNIT)
    EntityManagerFactory entityManagerFactory;
    
    protected EntityManager getFreshEntityManager()
    {
        return entityManagerFactory.createEntityManager();
    }

    @Override    
    /* (non-Javadoc)
     * @see javax.faces.validator.Validator#validate(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
     */
    public void validate(final FacesContext context, final UIComponent component,
            final Object value) throws ValidatorException
    {
        // throw validator with invalid entry message per default if validate() returns false
        if (!this.validate(value)) {
            throw new ValidatorException(Faces.newValidationErrorMessage("Invalid entry", "Invalid entry"));
        }
    }

    @Override
    /* (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.util.JumpUpValidator#validate(java.lang.Object)
     */
    public boolean validate(final Object value)
    {
        return false;
    }

}
