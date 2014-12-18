/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.navigation;

import java.net.MalformedURLException;
import java.net.URL;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import de.htw.fb4.imi.jumpup.ApplicationError;
import de.htw.fb4.imi.jumpup.settings.BeanNames;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 28.11.2014
 *
 */
@Named(value = BeanNames.NAVIGATION_BEAN)
@ApplicationScoped
public class NavigationBean implements NavigationOutcomes
{

    /**
     * Path to registration index.
     * @return
     */
    public static String toRegistration()
    {
        return "registration/";
    }

    /**
     * Return the path to the webapp.
     * @return
     */
    public static String pathToApp()
    {
        ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) extContext.getRequest();
        
        String contextPath = request.getContextPath();        

        URL reconstructedURL = null;
        try {
            reconstructedURL = new URL(request.getScheme(),
                                           request.getServerName(),
                                           request.getServerPort(),
                                           contextPath);
        } catch (MalformedURLException e) {
            throw new ApplicationError("Could not get URL to webapp.", NavigationBean.class);
        }
        
        return reconstructedURL.toString();
    }
    
    /**
     * Redirect to login page.
     * 
     * @return
     */
    public static String redirectToLogin()
    {
        return "index.xhtml?faces-redirect=true";
    }
    
    /**
     * 
     * @return
     */
    public String toUserProfileEdit()
    {
        return TO_USER_PROFILE;
    }
}
