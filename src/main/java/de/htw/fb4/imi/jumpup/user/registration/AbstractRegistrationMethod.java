/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.user.registration;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
    protected Set<String> errorMessages;

    @PersistenceContext(unitName=PersistenceSettings.PERSISTENCE_UNIT)
    protected EntityManager entityManager;    
    
    protected void reset()
    {
        this.errorMessages = new HashSet<String>();
    }
    

    /* (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.user.registration.RegistrationMethod#performRegistration(de.htw.fb4.imi.jumpup.user.registration.RegistrationBean)
     */
    @Override
    public void performRegistration(final RegistrationBean registrationBean)
    {
        this.reset();
        
        User newUser = new User();
        
        // fill user entity by registration bean
        newUser.setEmail(registrationBean.geteMail());
        newUser.setUsername(registrationBean.getUsername());
        newUser.setPassword(registrationBean.getPassword());
        newUser.setPrename(registrationBean.getPrename());
        newUser.setLastname(registrationBean.getLastname());
        
        try {
            entityManager.getTransaction().begin();
            
            entityManager.persist(newUser);
            
            entityManager.getTransaction().commit();
        } catch ( Exception e) {
            this.errorMessages.add("Sorry, an error occured during registration. Please contact the customer care.");
            try { entityManager.getTransaction().rollback(); } catch ( Exception e2) {}
            throw e;
        }
    }

    /* (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.user.registration.RegistrationMethod#sendConfirmationLinkMail(de.htw.fb4.imi.jumpup.user.registration.RegistrationBean)
     */
    @Override
    public void sendConfirmationLinkMail(RegistrationBean registrationBean)
    {
        // TODO  implement sending of success mail

    }

    /* (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.user.registration.RegistrationMethod#sendConfirmationSuccessMail(de.htw.fb4.imi.jumpup.user.registration.RegistrationBean)
     */
    @Override
    public void sendRegistrationSuccessMail(RegistrationBean registrationBean)
    {
        // TODO implement sending of success mail

    }

    /* (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.user.registration.RegistrationMethod#confirmRegistration(de.htw.fb4.imi.jumpup.user.registration.RegistrationBean)
     */
    @Override
    public void confirmRegistration(RegistrationBean registrationBean)
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
