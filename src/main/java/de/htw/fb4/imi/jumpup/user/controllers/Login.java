/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.user.controllers;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import de.htw.fb4.imi.jumpup.controllers.AbstractFacesController;
import de.htw.fb4.imi.jumpup.navigation.NavigationOutcomes;
import de.htw.fb4.imi.jumpup.settings.BeanNames;
import de.htw.fb4.imi.jumpup.user.login.LoginMethod;
import de.htw.fb4.imi.jumpup.user.login.LoginModel;

/**
 * <p>The Login controller is used for the login action and to check if the user is authenticated.</p>
 * 
 * <p>It is sessionscoped, so that you can access it from everywhere to get the current logged in user.</p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 27.11.2014
 *
 */
@Named( value = BeanNames.LOGIN_CONTROLLER )
@SessionScoped
public class Login extends AbstractFacesController implements Serializable
{
    /**
     * Serializable.
     */
    private static final long serialVersionUID = 6762395393648784704L;

    protected LoginModel loginModel = new LoginModel();
    
    @Inject
    protected LoginMethod loginMethod;

    /**
     * @return the loginModel
     */
    public LoginModel getLoginModel()
    {
        return loginModel;
    }
    
    
    /**
     * LogIn user action.
     * 
     * @return
     */
    public String loginUser()
    {
        try {       
            this.loginMethod.logIn(this.getLoginModel());  
            
            // registration was performed successfully, so redirect to success page
            if (!this.loginMethod.hasError()) {
                return NavigationOutcomes.LOGIN_SUCCESS;
            }

            // otherwise add all error messages
            for (final String errorMessage: this.loginMethod.getErrors()) {
                this.addDisplayErrorMessage(errorMessage);
            }            
        } catch ( Exception e ) {
            this.addDisplayErrorMessage("I was not able to log you in.");            
        }
        
        return NavigationOutcomes.LOGIN_FAILURE;
    }
    
    /**
     * Log-out user action.
     * @return
     */
    public String logoutUser()
    {
        try {       
            this.loginMethod.logOut(this.getLoginModel());  
            
            // registration was performed successfully, so redirect to success page
            if (!this.loginMethod.hasError()) {
                this.addDisplayInfoMessage("You were successfully logged out. We hope you enjoyed your stay.");
                return NavigationOutcomes.LOGOUT_SUCCESS;
            }

            // otherwise add all error messages
            for (final String errorMessage: this.loginMethod.getErrors()) {
                this.addDisplayErrorMessage(errorMessage);
            }            
        } catch ( Exception e ) {
            this.addDisplayErrorMessage("I was not able to log you out.");            
        }
        
        return NavigationOutcomes.LOGOUT_FAILURE;
    }


    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Login [loginModel=");
        builder.append(loginModel);
        builder.append(", loginMethod=");
        builder.append(loginMethod);
        builder.append("]");
        return builder.toString();
    }
    
    
}
