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
     * Simple validation message which takes any value and returns a boolean.
     * 
     * @param value
     * @return true on successful validation, false on validation failures.
     */
    boolean validate(final Object value);
    
    /**
     * <p>Define and return the minimum length that validated values should have.</p>
     * 
     * <p>This value could be read from a configuration system for example.</p> 
     * 
     * @return
     */
    int getMinLength();
    
    /**
     * <p>Define and return the maximum length that validated values should have.</p>
     * 
     * <p>This value could be read from a configuration system for example.</p> 
     * 
     * @return
     */
    int getMaxLength();
    
    /**
     * <p>Get the validation REGEX pattern to be used within the frontend.</p>
     * 
     * @return
     */
    String getFrontendValidationPattern();
}
