/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.user.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import de.htw.fb4.imi.jumpup.controller.AbstractFacesController;
import de.htw.fb4.imi.jumpup.navigation.NavigationBean;
import de.htw.fb4.imi.jumpup.navigation.NavigationOutcomes;
import de.htw.fb4.imi.jumpup.settings.BeanNames;
import de.htw.fb4.imi.jumpup.user.registration.RegistrationMethod;
import de.htw.fb4.imi.jumpup.user.registration.RegistrationModel;
import de.htw.fb4.imi.jumpup.user.registration.RegistrationSession;
import de.htw.fb4.imi.jumpup.user.util.HashGenerable;

/**
 * <p>This bean is filled by JSF during the registration.</p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 10.11.2014
 *
 */
@Named(value = BeanNames.REGISTRATION_CONTROLLER)
@RequestScoped
public class Registration extends AbstractFacesController
{
    @Inject
    protected RegistrationMethod registrationMethod;
    
    @Inject
    protected RegistrationSession registrationSession;
    
    @Inject
    protected HashGenerable hashGenerable;
    
    /**
     * 
     * @return
     */
    public RegistrationModel getRegistrationModel()
    {        
        return this.registrationSession.getRegistrationModel();
    }
    
    /**
     * 
     * @return
     */
    public RegistrationMethod getRegistrationMethod()
    {
        return this.registrationMethod;
    }
    
    /**
     * Registration action.
     * 
     * Try to register the user.
     * @return 
     */
    public String registerUser()
    {
        try {       
            this.registrationMethod.performRegistration(this.getRegistrationModel());  
            
            // registration was performed successfully, so redirect to success page
            if (!this.registrationMethod.hasError()) {
                this.sendConfirmationLinkOrSuccessMail();
                
                return NavigationOutcomes.REGISTRATION_SUCCESS;
            }

            // otherwise add all error messages
            showAllErrorMessagesFromRegistration();            
        } catch ( Exception e ) {
            this.addDisplayErrorMessage("Could not perform your registration.");            
        }
        
        // redirect to registration failure page
        return NavigationOutcomes.REGISTRATION_FAILURE;
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
        try {
            this.registrationMethod.confirmRegistration(this.getRegistrationModel());
            
            // if no error occured, send final registration success mail
            if (!this.registrationMethod.hasError()) {
                try {
                this.registrationMethod.sendRegistrationSuccessMail(this.getRegistrationModel());
                } catch (Exception e) {
                    this.addDisplayErrorMessage("Could not send the registration success mail, but your registration was successful. You can login now.");
                }
                
                this.addDisplayInfoMessage("You were confirmed successfully. You can now login.");
                return NavigationBean.redirectToLogin();                
            }
            // else:
            this.showAllErrorMessagesFromRegistration();            
        } catch (Exception e) {
            this.addDisplayErrorMessage("Could not confirm your registration. Please contact the customer care.");
        }
        
        return NavigationBean.REGISTRATION_CONFIRMATION_FAILURE;
    }
    
    private void sendConfirmationLinkOrSuccessMail()
    {
        try {
            // send confirmation link mail if neccessary
            if (this.registrationMethod.needsConfirmation()) {
                this.registrationMethod.sendConfirmationLinkMail(this.getRegistrationModel());
            } else {
                // immediatly send registration success mail
                this.registrationMethod.sendRegistrationSuccessMail(this.getRegistrationModel());
            }
            
            // show error messages
            if (this.registrationMethod.hasError()) {
                this.showAllErrorMessagesFromRegistration();
            }
        } catch (Exception e ) {
            this.addDisplayErrorMessage("We couldn't send the registration confirmation mail.");
        }      
    }
    
    protected void showAllErrorMessagesFromRegistration()
    {
        for (final String errorMessage: this.registrationMethod.getErrors()) {
            this.addDisplayErrorMessage(errorMessage);
        }
    }
    
}
