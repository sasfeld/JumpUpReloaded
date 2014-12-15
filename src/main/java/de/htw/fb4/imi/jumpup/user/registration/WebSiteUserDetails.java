/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.user.registration;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

import de.htw.fb4.imi.jumpup.Application;
import de.htw.fb4.imi.jumpup.Application.LogType;
import de.htw.fb4.imi.jumpup.settings.BeanNames;
import de.htw.fb4.imi.jumpup.settings.PersistenceSettings;
import de.htw.fb4.imi.jumpup.user.controllers.Login;
import de.htw.fb4.imi.jumpup.user.entities.User;
import de.htw.fb4.imi.jumpup.user.entities.UserDetails;

/**
 * <p>
 * Class manages the persisting of the userDetails. Triggers a rollback if an
 * error occurs and displays the errormessage.
 * </p>
 * 
 * @author <a href="mailto:m_seidler@hotmail.de">Marco Seidler</a>
 * @since 14.12.2014
 * 
 */
@Stateless(name = BeanNames.WEBSITE_USER_DETAILS)
public class WebSiteUserDetails implements UserDetailsMethod
{   
    @PersistenceUnit(unitName = PersistenceSettings.PERSISTENCE_UNIT)
    protected EntityManagerFactory entityManagerFactory;

    @Inject
    protected Login login;

    protected List<String> errors = new ArrayList<String>();

    /*
     * (non-Javadoc)
     * 
     * @see de.htw.fb4.imi.jumpup.util.ErrorPrintable#hasError()
     */
    @Override
    public boolean hasError()
    {
        if (errors.size() > 0) {
            return true;
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.htw.fb4.imi.jumpup.util.ErrorPrintable#getErrors()
     */
    @Override
    public String[] getErrors()
    {
        return errors.toArray(new String[errors.size()]);
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.htw.fb4.imi.jumpup.util.ErrorPrintable#getSingleErrorString()
     */
    @Override
    public String getSingleErrorString()
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    protected EntityManager getFreshEntityManager()
    {
        EntityManager em = this.entityManagerFactory.createEntityManager();
        
        return em;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.htw.fb4.imi.jumpup.user.registration.UserDetailsMethod#sendUserDetails
     * (de.htw.fb4.imi.jumpup.user.entities.UserDetails)
     */
    @Override
    public void sendUserDetails(final UserDetails userDetails)
    {
        EntityManager entityManager = this.getFreshEntityManager();
        try {
            User currentUser = login.getLoginModel().getCurrentUser();
            
            userDetails.setUser(currentUser);
            Application.log("WebSiteUserDetails: try to add userDetails",
                    LogType.DEBUG, getClass());
            entityManager.merge(userDetails);

            Application.log("WebSiteUserDetails: persist success",
                    LogType.DEBUG, getClass());
        } catch (Exception e) {
            Application.log(e.getMessage(), LogType.ERROR, getClass());
            try {entityManager.getTransaction().rollback();} catch (Exception e2) {}
            errors.add("Internal Server Error, please contact the provider or try again.");
        }

    }

}
