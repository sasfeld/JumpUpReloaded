/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.user.login;

import javax.ejb.Stateless;

import de.htw.fb4.imi.jumpup.ApplicationError;
import de.htw.fb4.imi.jumpup.ApplicationUserException;
import de.htw.fb4.imi.jumpup.settings.BeanNames;
import de.htw.fb4.imi.jumpup.user.entity.User;

/**
 * <p>
 * </p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 27.11.2014
 *
 */
@Stateless(name = BeanNames.WEBSITE_LOGIN)
public class WebSiteLogin extends AbstractLoginMethod implements LoginMethod
{
    private static final long serialVersionUID = -3100825117103531673L;

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.htw.fb4.imi.jumpup.user.login.LoginMethod#logIn(de.htw.fb4.imi.jumpup.
     * user.login.LoginModel)
     */
    @Override
    public void logIn(final LoginModel loginModel)
            throws ApplicationUserException
    {
        this.reset();

        // avoid double-login
        if (loginModel.isLoggedIn) {
            return;
        }

        try {
            User loggedInUser = this.lookForMatchingUser(loginModel);
            this.setLoggedIn(loginModel, loggedInUser);
        } catch (ApplicationError e) {
            this.errorMessages.add(
                    "An internal server error occured. Please contact the customer care.");
        }
    }

}
