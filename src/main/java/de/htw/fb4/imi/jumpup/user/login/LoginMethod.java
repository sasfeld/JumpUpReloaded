/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.user.login;

import javax.ejb.Local;

import de.htw.fb4.imi.jumpup.util.ErrorPrintable;

/**
 * <p>Login methods make sure that the user will be logged in for a whole session.</p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 27.11.2014
 *
 */
@Local
public interface LoginMethod extends ErrorPrintable
{

    /**
     * Try to login the user. Make sure to set the LoginModel:isLoggedIn and LoginModel:currentUser fields.
     * @param loginModel
     */
    void logIn(final LoginModel loginModel);

    /**
     * Try to log the user out. 
     * @param loginModel
     */
    void logOut(final LoginModel loginModel); 
    
    /**
     * Check if the user was registered freshly.
     * 
     * @param loginModel
     * @return
     */
    boolean isNew(final LoginModel loginModel);
}
