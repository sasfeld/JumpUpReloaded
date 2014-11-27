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

    String LOGIN_FAILURE = "index";
    
    String LOGIN_SUCCESS = "login_success";
    
    String LOGOUT_SUCCESS = "logout_success";
    
    String LOGOUT_FAILURE = "logout_failure";
}
