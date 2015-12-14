/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.user;

import javax.ejb.Local;

import de.htw.fb4.imi.jumpup.user.entity.User;

/**
 * <p>Data access object definition to work with {@link User} entities.</p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 30.01.2015
 *
 */
@Local
public interface UserDAO
{
    
    /**
     * Load {@link User} by identity.
     * @param identity
     * @return
     * @throws NoResultException if no user with given identity was found
     */
    User loadById(long identity);

}
