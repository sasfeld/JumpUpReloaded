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
import javax.persistence.PersistenceContext;

import de.htw.fb4.imi.jumpup.Application;
import de.htw.fb4.imi.jumpup.Application.LogType;
import de.htw.fb4.imi.jumpup.settings.BeanNames;
import de.htw.fb4.imi.jumpup.settings.PersistenceSettings;
import de.htw.fb4.imi.jumpup.user.controllers.Login;
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

    @PersistenceContext(unitName = PersistenceSettings.PERSISTENCE_UNIT)
    protected EntityManager entityManager;

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
        try {
            userDetails.setUser(login.getLoginModel().getCurrentUser());
            Application.log("WebSiteUserDetails: try to add userDetails",
                    LogType.DEBUG, getClass());
            entityManager.persist(userDetails);
            Application.log("WebSiteUserDetails: persist success",
                    LogType.DEBUG, getClass());
        } catch (Exception e) {
            Application.log(e.getMessage(), LogType.ERROR, getClass());
            entityManager.getTransaction().rollback();
            errors.add("Internal Server Error, please contact the provider or try again.");
        }

    }

}
