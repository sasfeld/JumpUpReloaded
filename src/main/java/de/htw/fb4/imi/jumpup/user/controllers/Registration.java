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
import de.htw.fb4.imi.jumpup.settings.BeanNames;
import de.htw.fb4.imi.jumpup.settings.NavigationOutcomes;
import de.htw.fb4.imi.jumpup.user.registration.RegistrationMethod;
import de.htw.fb4.imi.jumpup.user.registration.RegistrationModel;

/**
 * <p>This bean is filled by JSF during the registration.</p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 10.11.2014
 *
 */
@Named(value = BeanNames.REGISTRATION_CONTROLLER_BEAN)
@RequestScoped
public class Registration extends AbstractFacesController
{
    @Inject
    protected RegistrationMethod registrationMethod;
    protected RegistrationModel registrationModel = new RegistrationModel();
    
    /**
     * 
     * @return
     */
    public RegistrationModel getRegistrationModel()
    {        
        return this.registrationModel;
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
                return NavigationOutcomes.REGISTRATION_SUCCESS;
            }

            // otherwise add all error messages
            for (final String errorMessage: this.registrationMethod.getErrors()) {
                this.addDisplayErrorMessage(errorMessage);
            }            
        } catch ( Exception e ) {
            this.addDisplayErrorMessage("Could not perform your registration.");            
        }
        
        // redirect to registration failure page
        return NavigationOutcomes.REGISTRATION_FAILURE;
    }
    
}
