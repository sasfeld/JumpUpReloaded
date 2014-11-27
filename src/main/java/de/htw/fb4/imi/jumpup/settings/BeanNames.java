/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.settings;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 10.11.2014
 *
 */
public interface BeanNames
{

    String PASSWORD_HASH_GENERATOR = "password_hash_generator";
    
    String REGISTRATION_CONTROLLER = "registrationController";
    String LOGIN_CONTROLLER = "loginController";
    
    String WEBSITE_REGISTRATION = "website_registration";
    
    String TRANSLATOR = "translator";
    
    String USERNAME_VALIDATOR = "usernameValidator";
    String PASSWORD_VALIDATOR = "passwordValidator";
    String REPEAT_PASSWORD_VALIDATOR = "repeatPasswordValidator";
    String PRENAME_VALIDATOR = "prenameValidator";
    String LASTNAME_VALIDATOR = "lastnameValidator";
    String EMAIL_VALIDATOR = "emailValidator";
    String UsernameOrMailValidator = "emailOrUsernameValidator";
}
