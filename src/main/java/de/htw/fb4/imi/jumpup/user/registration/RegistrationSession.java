/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.user.registration;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import de.htw.fb4.imi.jumpup.settings.BeanNames;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 08.12.2015
 *
 */
@Named(value = BeanNames.REGISTRATION_SESSION)
@SessionScoped
public class RegistrationSession implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 668833627571353697L;

    private RegistrationModel registrationModel;    
    
    public RegistrationModel getRegistrationModel()
    {   
        if (null == registrationModel) {
            registrationModel = new RegistrationModel();
        }
        
        return registrationModel;
    }

    public void setRegistrationModel(RegistrationModel registrationModel2)
    {
       this.registrationModel = registrationModel2;        
    }    
}
