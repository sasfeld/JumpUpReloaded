/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup;

import java.net.MalformedURLException;
import java.net.URL;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import de.htw.fb4.imi.jumpup.settings.BeanNames;
import de.htw.fb4.imi.jumpup.util.WebAppFilter;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 02.01.2016
 *
 */
@ApplicationScoped
@Named(value = BeanNames.APPLICATION_PROPERTIES)
public class ApplicationProperties
{
    public static String CONTEXT_PATH;
    public static String SCHEME;
    public static String SERVER_NAME;
    public static int SERVER_PORT;
    
    private String jumpUpBasePath;
    private static URL url;
    
    public void setJumpUpBasePath(String jumpUpBasePath)
    {
        this.jumpUpBasePath = jumpUpBasePath;
        CONTEXT_PATH = jumpUpBasePath;
    }
    
    /**
     * Get the base path (servlet context) to the JumpUp web app.
     * @return
     */
    public String getJumpUpBasePath()
    {
        return jumpUpBasePath;
    }
    
    /**
     * Get the base URL to the JumpUp web app.
     * @return
     */
    public URL getJumpUpBaseUrl()
    {
        return getWebAppUrl();
    }
    
    /**
     * Get the path to the lookuptrips REST service.
     * @return
     */
    public String getLookUpTripsWebServicePath()
    {
        return getJumpUpBasePath() + "/rest/lookuptrips";
    }
    
    public static URL getWebAppUrl()
    {
        if (null == url) {
            buildUrl();
        }
        
        return url;
    }

    protected static void buildUrl()
    {
        try {
            url = new URL(SCHEME,
                    SERVER_NAME,
                    SERVER_PORT,
                    CONTEXT_PATH);
        } catch (MalformedURLException e) {
            throw new ApplicationError("Could not build URL to webapp.",
                    WebAppFilter.class);
        }
    }
    
}
