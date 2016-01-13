/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.user.login;

import javax.ejb.Local;

import de.htw.fb4.imi.jumpup.ApplicationUserException;

/**
 * <p>
 * Login methods make sure that the user will be logged in for a whole session.
 * </p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 27.11.2014
 *
 */
@Local
public interface LoginMethod
{

    /**
     * Try to login the user. Make sure to set the LoginModel:isLoggedIn and
     * LoginModel:currentUser fields.
     * 
     * @param loginModel
     * @throws ApplicationUserException
     */
    void logIn(final LoginModel loginModel) throws ApplicationUserException;

    /**
     * Try to log the user out.
     * 
     * @param loginModel
     * @throws ApplicationUserException
     */
    void logOut(final LoginModel loginModel) throws ApplicationUserException;

    /**
     * Check if the user was registered freshly.
     * 
     * @param loginModel
     * @return
     */
    boolean isNew(final LoginModel loginModel);
}
