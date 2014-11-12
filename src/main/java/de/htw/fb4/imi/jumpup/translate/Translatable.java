/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.translate;

import javax.ejb.Local;

/**
 * <p>Interface which defines methods for translation.</p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 12.11.2014
 *
 */
@Local
public interface Translatable
{

    /**
     * Translate the given message.
     * 
     * Return a new translated String.
     * 
     * @param message
     * @return
     */
    String translate(final String message);
}
