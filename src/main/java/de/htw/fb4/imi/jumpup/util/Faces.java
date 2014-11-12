/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.util;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

/**
 * <p>JSF util</p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 10.11.2014
 *
 */
public class Faces
{
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
    public static FacesMessage newFacesMessage(Severity severity,
            String message)
    {
        return new FacesMessage(severity, message, null);
    }
}