/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.user.validation;



import java.util.regex.Pattern;

import javax.faces.bean.RequestScoped;
import javax.inject.Named;
import javax.persistence.Query;

import de.htw.fb4.imi.jumpup.ApplicationError;
import de.htw.fb4.imi.jumpup.settings.BeanNames;
import de.htw.fb4.imi.jumpup.user.entity.User;
import de.htw.fb4.imi.jumpup.user.util.ConfigReader;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 24.11.2014
 *
 */
@Named( value = BeanNames.EMAIL_VALIDATOR)
@RequestScoped
public class EMail extends AbstractUserValidator
{   
    public static String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    
    private boolean dbCheck = true;

    @Override
    /* (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.util.JumpUpValidator#validate(java.lang.Object)
     */
    public boolean validate(final Object value)
    {
        final String eMail = ((String) value).trim();
        
        if (!this.checkLength(eMail)
                || !this.checkCharacters(eMail)
                || this.existsAlready(eMail)) {
                return false;
            }
        
        return true;
    }

    /**
     * Check if eMail is already registered.
     * @param eMail
     * @return
     */
    private boolean existsAlready(final String eMail)
    {
        // check if dbCheck is disabled
        if (!this.dbCheck) {
            return false;
        }
        
        // check if given eMail matches the current one of the logged in user
        if (this.isCurrentUsersEmail(eMail)) {
            return false;
        }
        
        if (null == entityManager) {
            throw new ApplicationError(getClass() + ":validate(): EntityManager is null. Please check why no entity manager is injected.", getClass());
        }
         
         // check whether more than user already exists.
         Query query = entityManager.createNamedQuery(User.NAME_QUERY_BY_EMAIL).
                 setParameter("email", eMail);
         if ( 0 < query.getResultList().size()) {
             this.errorMessages.add("The eMail address is already registered on JumpUp. Please use the forgot password function.");
             
             return true;
         }
         
         return false;
    }

    private boolean isCurrentUsersEmail(String eMail)
    {
        if (null != this.loginSession && null != this.loginSession.getCurrentUser()
                && this.loginSession.getCurrentUser().geteMail().equals(eMail)) {
            return true;
        }
        
        return false;
    }

    /**
     * Check length from user.properties config.
     * @param prename
     * @return
     */
    private boolean checkCharacters(final String eMail)
    {
        Pattern pMail = Pattern.compile(EMAIL_PATTERN);
        
        if (!pMail.matcher(eMail).matches()) {
            this.errorMessages.add("The eMail you entered does not appear to be correct.");
            
            return false;
        }
        
        return true;
    }

    protected boolean checkLength(final String eMail)
    {
        // get values from user.properties configuration file
        int minLength = this.getMinLength();
        int maxLength = this.getMaxLength();
        
        if (eMail.length() < minLength) {
            this.errorMessages.add("The eMail you entered is too short. Please type in at least " + minLength + " characters.");
            
            return false;
        } else if (eMail.length() > maxLength) {
            this.errorMessages.add("The eMail you entered is too long. Please type in maximum " + maxLength + " characters.");
            
            return false;
        }
        
        return true;
    }

    /**
     * En- or disable the database check of the username.
     * 
     * @param enabled
     */
    public void enableDBCheck(boolean enabled)
    {
        this.dbCheck = enabled;        
    }

    /**
     * 
     * @param userConfigReader
     */
    public void setConfigReader(ConfigReader userConfigReader)
    {
       this.userConfigReader = userConfigReader;        
    }

    @Override
    /*
     * (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.validator.JumpUpValidator#getMinLength()
     */
    public int getMinLength()
    {
        return 5;
    }

    @Override
    /*
     * (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.validator.JumpUpValidator#getMaxLength()
     */
    public int getMaxLength()
    {
        return 255;
    }
    
    /*
     * (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.validator.AbstractValidator#getDefaultFailureMessage()
     */
    protected String getDefaultFailureMessage()
    {
        return "You entered an invalid eMail.";
    }
}
