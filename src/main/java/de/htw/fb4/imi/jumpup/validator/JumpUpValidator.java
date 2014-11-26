/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.validator;

/**
 * <p>Simple validator interface.</p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 24.11.2014
 *
 */
public interface JumpUpValidator
{
    /**
     * Simpel validation message which takes any value and returns a boolean.
     * 
     * @param value
     * @return true on successful validation, false on validation failures.
     */
    boolean validate(final Object value);
}
