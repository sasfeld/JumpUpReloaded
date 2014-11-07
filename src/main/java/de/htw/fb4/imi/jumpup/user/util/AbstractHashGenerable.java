/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.user.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 07.11.2014
 *
 */
public abstract class AbstractHashGenerable implements HashGenerable
{
    public static final String SHA_256 = "SHA-256";

    /**
     * Get SHA-256 digest instance.
     * 
     * @return
     */
    protected MessageDigest getSha256Digest()
    {
        try {
            return MessageDigest.getInstance(SHA_256);
        } catch (final NoSuchAlgorithmException exception) {
            throw new AssertionError(SHA_256 + " instance not found. Please make sure that the algorithm is spelled correctly and supported by your java runtime.");
        }
    }
}
