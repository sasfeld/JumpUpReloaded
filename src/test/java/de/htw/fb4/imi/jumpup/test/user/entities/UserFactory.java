/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.test.user.entities;

import de.htw.fb4.imi.jumpup.user.entities.User;
import de.htw.fb4.imi.jumpup.user.util.HashGenerable;
import de.htw.fb4.imi.jumpup.user.util.PasswordHashGenerator;

/**
 * <p>Factory for non-integration unittests in the user module.</p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 07.11.2014
 *
 */
public class UserFactory
{

    public static User newUser()
    {
        User newUser = new User();
        newUser.setHashGenerable(newPasswordHashGenerable());
        
        return newUser;        
    }
    
    public static HashGenerable newPasswordHashGenerable()
    {
        return new PasswordHashGenerator();
    }
    
}
