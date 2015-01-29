/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.navigation;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.exception.ExceptionUtils;

import de.htw.fb4.imi.jumpup.Application;
import de.htw.fb4.imi.jumpup.Application.LogType;
import de.htw.fb4.imi.jumpup.ApplicationError;
import de.htw.fb4.imi.jumpup.settings.BeanNames;
import de.htw.fb4.imi.jumpup.trip.entities.Trip;

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

    private static final String UTF_8 = "UTF-8";

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
    
    public String toAddTrip()
    {
        return TO_ADD_TRIP;
    }
    
    public String toEditTrip(Integer tripId)
    {
        if (null == tripId) {
            return toAddTrip();
        }
        
        return pathToApp() + "/portal/trip/create.xhtml" + "?trip=" + tripId;
    }
    
    public String toCancelTrip(Integer tripId)
    {
        if (null == tripId) {
            return toAddTrip();
        }
        
        return pathToApp() + "/portal/trip/cancel.xhtml" + "?trip=" + tripId;
    }
    
    public String toListOfferedTrips()
    {
        return TO_LIST_OFFERED_TRIPS;
    }
    
    public String toLookForTrips()
    {
        return TO_LOOK_FOR_TRIPS;
    }
    
    public String toAddBooking()
    {
        return pathToApp() + "/portal/trip/booking.xhtml";
    }
    
    /**
     * Generate the URL to the booking facelet by the given trip.
     * @param trip
     * @return
     */
    public String generateAddBookingUrl(Trip trip)
    {
        StringBuilder urlBuilder = new StringBuilder();
        
        try {
            appendUrlEncoded(urlBuilder, "t", toString(trip.getIdentity()), '?');
            appendUrlEncoded(urlBuilder, "s", trip.getStartpoint(), '&');
            appendUrlEncoded(urlBuilder, "e", trip.getEndpoint(), '&');
            appendUrlEncoded(urlBuilder, "sL", toString(trip.getLatStartpoint()), '&');
            appendUrlEncoded(urlBuilder, "sLo", toString(trip.getLongStartpoint()), '&');
            appendUrlEncoded(urlBuilder, "eL", toString(trip.getLatEndpoint()), '&');
            appendUrlEncoded(urlBuilder, "eLo", toString(trip.getLongEndpoint()), '&');
            appendUrlEncoded(urlBuilder, "h", trip.createBookingHash(), '&');
        } catch (UnsupportedEncodingException e) {
            Application.log("generateAddBookingUrl: cannot generate booking URL due to exception " 
                    + e.getMessage() + "\nstack:" + ExceptionUtils.getFullStackTrace(e) , LogType.ERROR, getClass());
        }
        
        return toAddBooking() + urlBuilder.toString();
    }

    private String toString(float value)
    {
       return Float.toString(value);
    }
    
    private String toString(long value)
    {
        return Long.toString(value);
    }

    private void appendUrlEncoded(StringBuilder urlBuilder, String key,
            String value, char delimiter) throws UnsupportedEncodingException
    {
       urlBuilder.append(URLEncoder.encode(delimiter + key + "=" + value, UTF_8));        
    }
}
