/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.user.login;

import de.htw.fb4.imi.jumpup.ApplicationError;
import de.htw.fb4.imi.jumpup.user.entities.User;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 27.11.2014
 *
 */
public class WebSiteLogin extends AbstractLoginMethod
{
    private static final long serialVersionUID = -3100825117103531673L;

    /* (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.user.login.LoginMethod#logIn(de.htw.fb4.imi.jumpup.user.login.LoginModel)
     */
    @Override
    public void logIn(final LoginModel loginModel)
    {
        this.reset();
        
        // avoid double-login
        if (loginModel.isLoggedIn) {            
            return;
        }
        
        try {
            User loggedInUser = this.lookForMatchingUser(loginModel);
            
            if (null == loggedInUser) {
                this.errorMessages.add("Invalid credentials. Please try again.");
                return;
            }
            
            this.setLoggedIn(loginModel, loggedInUser);
        } catch ( ApplicationError e) {
            this.errorMessages.add("An internal server error occured. Please contact the customer care.");
        }
    }
    
    
}
