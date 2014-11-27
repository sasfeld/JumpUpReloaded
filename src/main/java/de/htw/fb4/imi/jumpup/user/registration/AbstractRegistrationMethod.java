/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.user.registration;

import java.util.HashSet;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.htw.fb4.imi.jumpup.settings.PersistenceSettings;
import de.htw.fb4.imi.jumpup.user.entities.User;
import de.htw.fb4.imi.jumpup.user.util.HashGenerable;
import de.htw.fb4.imi.jumpup.util.StringUtil;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 10.11.2014
 *
 */
public abstract class AbstractRegistrationMethod implements RegistrationMethod
{
    protected HashSet<String> errorMessages;
    
    @Inject
    protected HashGenerable hashGenerator;
    
    @PersistenceContext(unitName = PersistenceSettings.PERSISTENCE_UNIT)
    protected EntityManager entityManager;

    protected void reset()
    {
        this.errorMessages = new HashSet<String>();
    }   

    protected void persistInTransaction(final User newUser)
    {         
        this.entityManager.persist(newUser);            
    }

    protected void tryToRollbackAndThrow()
    {
        this.errorMessages.add("Sorry, an error occured during registration. Please contact the customer care.");
        try { this.entityManager.getTransaction().rollback(); } catch ( Exception e2) {}
    }

    protected User createAndFillUser(final RegistrationModel registrationModel)
    {
        User newUser = new User();
        
        // inject hash generator
        newUser.setHashGenerable(this.hashGenerator);
        
        // fill user entity
        newUser.setEmail(registrationModel.geteMail());
        newUser.setUsername(registrationModel.getUsername());
        newUser.setPassword(registrationModel.getPassword());
        newUser.setPrename(registrationModel.getPrename());
        newUser.setLastname(registrationModel.getLastname());
        newUser.setIsConfirmed(false);
        
        return newUser;
    }

    /* (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.user.registration.RegistrationMethod#sendConfirmationLinkMail(de.htw.fb4.imi.jumpup.user.registration.RegistrationBean)
     */
    @Override
    public void sendConfirmationLinkMail(final RegistrationModel registrationModel)
    {
        // TODO  implement sending of success mail

    }

    /* (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.user.registration.RegistrationMethod#sendConfirmationSuccessMail(de.htw.fb4.imi.jumpup.user.registration.RegistrationBean)
     */
    @Override
    public void sendRegistrationSuccessMail(final RegistrationModel registrationModel)
    {
        // TODO implement sending of success mail

    }

    /* (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.user.registration.RegistrationMethod#confirmRegistration(de.htw.fb4.imi.jumpup.user.registration.RegistrationBean)
     */
    @Override
    public void confirmRegistration(final RegistrationModel registrationModel)
    {
        // TODO implement confirmation functionality

    }    

    @Override
    /*
     * (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.util.ErrorPrintable#hasError()
     */
    public boolean hasError()
    {
        if (null == this.errorMessages) {
            return false;
        }
        
        return this.errorMessages.size() > 0;
    }

    @Override
    /*
     * (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.util.ErrorPrintable#getErrors()
     */
    public String[] getErrors()
    {
        if (null == this.errorMessages) {
            return new String[0];
        }
        
        return this.errorMessages.toArray(new String[this.errorMessages.size()]);
    }
    
    @Override
    /*
     * (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.util.ErrorPrintable#getSingleErrorString()
     */
    public String getSingleErrorString()
    {
        if (this.hasError()) {
            return StringUtil.buildString(this.getErrors(), "<br />");
        }
        
        return "";
    }

}
