/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.user.registration;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import de.htw.fb4.imi.jumpup.user.entities.User;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 10.11.2014
 *
 */
public abstract class AbstractRegistrationMethod implements RegistrationMethod
{
    @Inject
    protected EntityManager entityManager;

    /* (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.user.registration.RegistrationMethod#performRegistration(de.htw.fb4.imi.jumpup.user.registration.RegistrationBean)
     */
    @Override
    public void performRegistration(RegistrationBean registrationBean)
    {
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

}
