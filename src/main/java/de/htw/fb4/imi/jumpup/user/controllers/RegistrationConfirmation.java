/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.user.controllers;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import de.htw.fb4.imi.jumpup.controllers.AbstractFacesController;
import de.htw.fb4.imi.jumpup.navigation.NavigationBean;
import de.htw.fb4.imi.jumpup.settings.BeanNames;
import de.htw.fb4.imi.jumpup.user.registration.RegistrationMethod;
import de.htw.fb4.imi.jumpup.user.registration.RegistrationModel;

/**
 * <p>This controller is called when the user confirmation is activated and the user clicks the confirmation link in the eMail.</p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 03.12.2014
 *
 */
@Named( value = BeanNames.REGISTRATION_CONFIRMATION_CONTROLLER )
@RequestScoped
public class RegistrationConfirmation extends AbstractFacesController
{
    @Inject
    protected RegistrationMethod registrationMethod;
    
    protected String username;
    
    protected String userHash;
    
    protected RegistrationModel registrationModel = new RegistrationModel();
    
    
    public RegistrationModel getRegistrationModel()
    {
        return this.registrationModel;
    }
    
    /**
     * @return the username
     */
    public String getUsername()
    {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username)
    {
        this.username = username;
    }

    /**
     * @return the userHash
     */
    public String getUserHash()
    {
        return userHash;
    }

    /**
     * @param userHash the userHash to set
     */
    public void setUserHash(String userHash)
    {
        this.userHash = userHash;
    }

    /**
     * Confirm user action.
     * 
     * This action is expected to be called with two HTTP parameters: user and hash.
     * 
     * @return
     */
    public String confirmUser()
    {
        this.prepareRegistrationModel();
        
        try {
            this.registrationMethod.confirmRegistration(this.registrationModel);
            
            // if no error occured, send final registration success mail
            if (!this.registrationMethod.hasError()) {
                this.registrationMethod.sendRegistrationSuccessMail(this.registrationModel);
                return NavigationBean.redirectToLogin();                
            }
            // else:
            this.showAllErrorMessagesFromRegistration();            
        } catch (Exception e) {
            this.addDisplayErrorMessage("Could not confirm your registration. Please contact the customer care.");
        }
        
        return NavigationBean.REGISTRATION_CONFIRMATION_FAILURE;
    }

    /**
     * Fill HTTP request parameters in registration model.
     */
    private void prepareRegistrationModel()
    {
        this.registrationModel.setUsername(this.username);
        this.registrationModel.setHashForConfirmation(this.userHash);
    }
    
    protected void showAllErrorMessagesFromRegistration()
    {
        for (final String errorMessage: this.registrationMethod.getErrors()) {
            this.addDisplayErrorMessage(errorMessage);
        }
    }
}
