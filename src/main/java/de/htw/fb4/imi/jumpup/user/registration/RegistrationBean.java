/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.user.registration;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import de.htw.fb4.imi.jumpup.settings.BeanNames;
import de.htw.fb4.imi.jumpup.util.AbstractFacesBean;

/**
 * <p>This bean is filled by JSF during the registration.</p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 10.11.2014
 *
 */
@ManagedBean(name=BeanNames.REGISTRATION_BEAN)
@SessionScoped
public class RegistrationBean extends AbstractFacesBean
{
    protected String username;
    
    protected String eMail;
    protected String confirmeMail;
    
    protected String prename;
    protected String lastname;
    
    protected String password;
    protected String confirmPassword;
    
    @Inject
    protected RegistrationMethod registrationMethod;
    
    /**
     * @return the username
     */
    public final String getUsername()
    {
        return username;
    }
    
    /**
     * @param username the username to set
     */
    public final void setUsername(String username)
    {
        this.username = username;
    }
    
    /**
     * @return the eMail
     */
    public final String geteMail()
    {
        return eMail;
    }
    
    /**
     * @param eMail the eMail to set
     */
    public final void seteMail(String eMail)
    {
        this.eMail = eMail;
    }
    
    /**
     * @return the confirmeMail
     */
    public final String getConfirmeMail()
    {
        return confirmeMail;
    }
    
    /**
     * @param confirmeMail the confirmeMail to set
     */
    public final void setConfirmeMail(String confirmeMail)
    {
        this.confirmeMail = confirmeMail;
    }
    
    /**
     * @return the prename
     */
    public final String getPrename()
    {
        return prename;
    }
    
    /**
     * @param prename the prename to set
     */
    public final void setPrename(String prename)
    {
        this.prename = prename;
    }
    
    /**
     * @return the lastname
     */
    public final String getLastname()
    {
        return lastname;
    }
    
    /**
     * @param lastname the lastname to set
     */
    public final void setLastname(String lastname)
    {
        this.lastname = lastname;
    }
    
    /**
     * @return the password
     */
    public final String getPassword()
    {
        return password;
    }
    
    /**
     * @param password the password to set
     */
    public final void setPassword(String password)
    {
        this.password = password;
    }
    
    /**
     * @return the confirmPassword
     */
    public final String getConfirmPassword()
    {
        return confirmPassword;
    }
    
    /**
     * @param confirmPassword the confirmPassword to set
     */
    public final void setConfirmPassword(String confirmPassword)
    {
        this.confirmPassword = confirmPassword;
    }
    
    /**
     * Register the user.
     */
    public final void registerUser()
    {
        try {
            this.registrationMethod.performRegistration(this);
        } catch ( Exception e ) {
            this.displayErrorMessage("Could not perform your registration.");
        }
    }
    
}
