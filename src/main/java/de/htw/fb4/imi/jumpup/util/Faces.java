/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.util;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.inject.Inject;

import de.htw.fb4.imi.jumpup.translate.Translatable;

/**
 * <p>JSF util</p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 10.11.2014
 *
 */
public class Faces
{
    @Inject
    protected static Translatable translator;
    
    /**
     * Main method to add error messages to be shown in JSF frontend.
     * @param message
     */
    public static final void addErrorMessage(final String message)
    {
        FacesContext.getCurrentInstance().addMessage(null, newFacesMessage(FacesMessage.SEVERITY_ERROR, message));
    }

    /**
     * Add an info message that will be shown in JSF frontend.
     * @param message
     */
    public static final void addInfoMessage(final String message)
    {
        FacesContext.getCurrentInstance().addMessage(null, newFacesMessage(FacesMessage.SEVERITY_INFO, message));
    }
    
    /**
     * Create a new JSF message. Make sure to do certain work like translations before the creation.
     * @param severity
     * @param message
     * @return
     */
    public static FacesMessage newFacesMessage(final Severity severity,
            final String message)
    {
        return new FacesMessage(severity, translator.translate(message), null);
    }

    /**
     * Create a new JSF validation error message. This is thrown by {@link Validator} instances on validation failures in general.
     * @param summary
     * @param detail
     * @return
     */
    public static FacesMessage newValidationErrorMessage(final String summary,
            final String detail)
    {
        FacesMessage msg = newFacesMessage(FacesMessage.SEVERITY_ERROR, summary);        
        msg.setDetail(detail);
        
        return msg;
    }
}
