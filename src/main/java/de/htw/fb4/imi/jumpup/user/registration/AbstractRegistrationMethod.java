/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.user.registration;

import java.util.HashSet;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.htw.fb4.imi.jumpup.Application;
import de.htw.fb4.imi.jumpup.Application.LogType;
import de.htw.fb4.imi.jumpup.settings.PersistenceSettings;
import de.htw.fb4.imi.jumpup.user.entities.User;
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
    
    @PersistenceContext(unitName = PersistenceSettings.PERSISTENCE_UNIT)
    protected EntityManager entityManager;

    protected void reset()
    {
        this.errorMessages = new HashSet<String>();
    }
    

    /* (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.user.registration.RegistrationMethod#performRegistration(de.htw.fb4.imi.jumpup.user.registration.RegistrationBean)
     */
    @Override
    public void performRegistration(final RegistrationModel registrationModel)
    {
        this.reset();
        
        final User newUser = createAndFillUser(registrationModel);
        
        try {
            // perform logging if not entity manager is given
            if (null == entityManager) {
                Application.log(getClass() + ":performRegistration(): EntityManager is null. Please check why no entity manager is injected.", LogType.CRITICAL, getClass());
                throw new NullPointerException("entityManager is null");
            }
            
            persistInTransaction(newUser);
        } catch ( Exception e) {
            tryToRollbackAndThrow();
            throw e;
        }
    }


    protected void persistInTransaction(final User newUser)
    {
        this.entityManager.getTransaction().begin();            
        this.entityManager.persist(newUser);            
        this.entityManager.getTransaction().commit();
    }

    protected void tryToRollbackAndThrow()
    {
        this.errorMessages.add("Sorry, an error occured during registration. Please contact the customer care.");
        try { this.entityManager.getTransaction().rollback(); } catch ( Exception e2) {}
    }

    protected User createAndFillUser(final RegistrationModel registrationModel)
    {
        User newUser = new User();
        
        // fill user entity
        newUser.setEmail(registrationModel.geteMail());
        newUser.setUsername(registrationModel.getUsername());
        newUser.setPassword(registrationModel.getPassword());
        newUser.setPrename(registrationModel.getPrename());
        newUser.setLastname(registrationModel.getLastname());
        
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
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    /*
     * (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.util.ErrorPrintable#getErrors()
     */
    public String[] getErrors()
    {
        // TODO Auto-generated method stub
        return null;
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
