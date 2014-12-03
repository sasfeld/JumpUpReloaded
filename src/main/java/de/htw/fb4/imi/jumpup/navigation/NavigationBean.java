/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.navigation;

import java.net.MalformedURLException;
import java.net.URL;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import de.htw.fb4.imi.jumpup.ApplicationError;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 28.11.2014
 *
 */
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
        
        String file = request.getRequestURI();

        URL reconstructedURL = null;
        try {
            reconstructedURL = new URL(request.getScheme(),
                                           request.getServerName(),
                                           request.getServerPort(),
                                           file);
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
}
