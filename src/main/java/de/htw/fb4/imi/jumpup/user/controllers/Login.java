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

import de.htw.fb4.imi.jumpup.Application;
import de.htw.fb4.imi.jumpup.Application.LogType;
import de.htw.fb4.imi.jumpup.controllers.AbstractFacesController;
import de.htw.fb4.imi.jumpup.navigation.NavigationBean;
import de.htw.fb4.imi.jumpup.navigation.NavigationOutcomes;
import de.htw.fb4.imi.jumpup.settings.BeanNames;
import de.htw.fb4.imi.jumpup.user.login.LoginMethod;
import de.htw.fb4.imi.jumpup.user.login.LoginModel;
import de.htw.fb4.imi.jumpup.util.Gender;

/**
 * <p>
 * The Login controller is used for the login action and to check if the user is
 * authenticated.
 * </p>
 * 
 * <p>
 * It is sessionscoped, so that you can access it from everywhere to get the
 * current logged in user.
 * </p>
 * 
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 27.11.2014
 * 
 */
@Named(value = BeanNames.LOGIN_CONTROLLER)
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
    
    @Inject
    protected NavigationBean navigationBean;

    protected String pathToApp;
    
    public String getIconUrl()
    {
        if (null == this.getLoginModel().getCurrentUser() 
                || null == this.getLoginModel().getCurrentUser().getUserDetails()
                || null == this.getLoginModel().getCurrentUser().getUserDetails().getGender()
                || this.getLoginModel().getCurrentUser().getUserDetails().getGender().equals(Gender.MAN)) {
            return navigationBean.pathToAppFallback()+ "/resources/img/icons/male.png";
        } else if (this.getLoginModel().getCurrentUser().getUserDetails().getGender().equals(Gender.LADYBOY)) {
            return navigationBean.pathToAppFallback()+ "/resources/img/icons/ladyboy.png"; 
        }
        
        return navigationBean.pathToAppFallback()+ "/resources/img/icons/female.png";
    }
    
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

            Application.log("Login: User tried to login.", LogType.INFO,
                    getClass());
            // registration was performed successfully, so redirect to success
            // page
            if (!this.loginMethod.hasError()) {
                this.storeSessionInformation();
                // redirect to user's profile page if he was registered new
                if (this.loginMethod.isNew(this.getLoginModel())) {
                    this.addDisplayInfoMessage("Please fill in your profile information so that the other users know you better.");
                    return NavigationOutcomes.TO_USER_PROFILE;
                }
                
                // otherwise to default login page
                return NavigationOutcomes.LOGIN_SUCCESS;
            }

            // otherwise add all error messages
            for (final String errorMessage : this.loginMethod.getErrors()) {
                this.addDisplayErrorMessage(errorMessage);
            }
        } catch (Exception e) {
            Application.log("loginUser(): " + e.getLocalizedMessage(), LogType.CRITICAL, getClass());
            this.addDisplayErrorMessage("I was not able to log you in.");
        }

        return NavigationOutcomes.LOGIN_FAILURE;
    }

    /**
     * Store user-related session information.
     */
    private void storeSessionInformation()
    {
        this.pathToApp = NavigationBean.pathToApp();
    }

    /**
     * Log-out user action.
     * 
     * @return
     */
    public String logoutUser()
    {
        try {
            this.loginMethod.logOut(this.getLoginModel());

            // registration was performed successfully, so redirect to success
            // page
            if (!this.loginMethod.hasError()) {
                this.addDisplayInfoMessage("You were successfully logged out. We hope you enjoyed your stay.");
                return NavigationOutcomes.LOGOUT_SUCCESS;
            }

            // otherwise add all error messages
            for (final String errorMessage : this.loginMethod.getErrors()) {
                this.addDisplayErrorMessage(errorMessage);
            }
        } catch (Exception e) {
            this.addDisplayErrorMessage("I was not able to log you out.");
        }

        return NavigationOutcomes.LOGOUT_FAILURE;
    }

    /*
     * (non-Javadoc)
     * 
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

    /**
     * Get the path to the web app as saved in the session.
     * @return
     */
    public String getPathToApp()
    {        
        if (null == this.pathToApp) {
            this.pathToApp = NavigationBean.pathToApp();
        }
        
        return pathToApp;
    }

}
