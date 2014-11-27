/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.user.login;

import java.util.HashSet;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import de.htw.fb4.imi.jumpup.ApplicationError;
import de.htw.fb4.imi.jumpup.settings.PersistenceSettings;
import de.htw.fb4.imi.jumpup.user.entities.User;
import de.htw.fb4.imi.jumpup.user.util.HashGenerable;
import de.htw.fb4.imi.jumpup.util.StringUtil;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 27.11.2014
 *
 */
public abstract class AbstractLoginMethod implements LoginMethod
{    
    @Inject
    protected HashGenerable hashGenerator;
    
    @PersistenceContext(unitName = PersistenceSettings.PERSISTENCE_UNIT)
    protected EntityManager entityManager;

    protected HashSet<String> errorMessages;
    
    protected void reset()
    {
        this.errorMessages = new HashSet<String>();
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
   
    protected void setLoggedIn(final LoginModel loginModel, final User loggedInUser)
    {
        loginModel.setCurrentUser(loggedInUser);
        loginModel.setLoggedIn(true);
    }

    protected User lookForMatchingUser(final LoginModel loginModel) throws ApplicationError
    {
        Query qAuthentication = this.entityManager
                .createNamedQuery(User.NAME_QUERY_LOGIN)
                .setParameter("token", loginModel.getUsernameOrMail())
                .setParameter("passwordHash", this.getHash(loginModel.getPassword()));
        
        try {
            User authenticatedUser = (User) qAuthentication.getSingleResult();
            
            // User isn't confirmed yet.
            if (!authenticatedUser.getIsConfirmed()) {
                this.errorMessages.add("You weren't confirmed yet. Please check your eMails to confirm your account.");
                return null;
            }
            
            return authenticatedUser;
        } catch (NoResultException e) {
            // no user found
            return null;
        }     
    }

    protected byte[] getHash(final String password) throws ApplicationError
    {
        if (null == this.hashGenerator) {
            throw new ApplicationError("HashGenerator wasn't injected.", getClass());
        }
        
        return this.hashGenerator.generateHash(password);
    }

    /* (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.user.login.LoginMethod#logOut(de.htw.fb4.imi.jumpup.user.login.LoginModel)
     */
    @Override
    public void logOut(final LoginModel loginModel)
    {
        this.reset();
    }

}
