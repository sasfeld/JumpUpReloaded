/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.navigation;

/**
 * <p>Collection of outcomes that are defined within the faces_config.xml.</p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 24.11.2014
 *
 */
public interface NavigationOutcomes
{    
    String REGISTRATION_SUCCESS = "registration_success";
    
    String REGISTRATION_FAILURE = "registration_failure";
    
    String REGISTRATION_CONFIRMATION_FAILURE = "registration_confirmation_failure";

    String LOGIN_FAILURE = "index";
    
    String LOGIN_SUCCESS = "login_success";
    
    String LOGOUT_SUCCESS = "/index.xhtml?faces-redirect=true";
    
    String LOGOUT_FAILURE = "logout_failure";
    
    String TO_USER_PROFILE = "to_user_profile"; 
    
    String TO_ADD_TRIP = "to_add_trip";
    
    String TO_EDIT_TRIP = "to_edit_trip";
    
    String TO_CANCEL_TRIP = "to_cancel_trip";
    
    String TO_LIST_OFFERED_TRIPS = "to_list_offered_trips";
    
    String TO_LOOK_FOR_TRIPS = "to_look_for_trips";    

    String TO_LIST_BOOKINGS = "to_list_bookings";
    
    String TO_REGISTRATION = "to_registration";
   
}
