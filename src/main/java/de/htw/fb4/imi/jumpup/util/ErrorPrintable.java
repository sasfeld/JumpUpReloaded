/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.util;

import de.htw.fb4.imi.jumpup.ApplicationUserException;

/**
 * <p>Error printable models can define error methods that need to be presented to the user in the frontend.</p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 24.11.2014
 *
 *@deprecated use {@link ApplicationUserException} instead
 */
public interface ErrorPrintable
{

    /**
     * Check whether an error was raised.
     * @return
     */
    boolean hasError();
    
    /**
     * Return all error messages.
     * 
     * Don't translate the error messages. This should be done by the client.
     * 
     * @return
     */
    String[] getErrors();
    
    /**
     * Get a single error string using the given delimiter (such as "\n").
     * @return
     */
    String getSingleErrorString();
}
