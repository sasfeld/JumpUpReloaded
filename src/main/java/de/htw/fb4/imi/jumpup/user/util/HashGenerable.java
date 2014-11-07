/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.user.util;

/**
 * <p>Interface for classes that generate hash values.</p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 07.11.2014
 *
 */
public interface HashGenerable
{    
    /**
     * Generate a hash from a given secret.
     * 
     * @param rawSecret
     * @return
     */
    byte[] generateHash( final String rawSecret );
}
