/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.settings;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 10.11.2014
 *
 */
public interface PersistenceSettings
{
    /**
     * Name of the persistence unit to be used.
     */
    String PERSISTENCE_UNIT = "database";  
    
    /**
     * Max length of strings that need to be longer than VARCHAR(255) -> default.
     */
    int LONG_TEXT_MAX_LENGTH = 1000000;
}
