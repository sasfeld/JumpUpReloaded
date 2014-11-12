/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.util;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 10.11.2014
 *
 */
public class AbstractFacesBean
{

    protected void displayInfoMessage(final String infoMessage)
    {
        Faces.addInfoMessage(infoMessage);
    }
    
    protected void displayErrorMessage(final String errorMessage)
    {
        Faces.addErrorMessage(errorMessage);
    }
}
