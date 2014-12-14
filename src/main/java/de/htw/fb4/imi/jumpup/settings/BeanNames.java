/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.settings;

/**
 * <p>
 * </p>
 * 
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 10.11.2014
 * 
 */
public interface BeanNames
{

    String PASSWORD_HASH_GENERATOR = "password_hash_generator";

    String REGISTRATION_CONTROLLER = "registrationController";
    String USER_DETAILS_CONTROLLER = "userDetailsController";
    String REGISTRATION_CONFIRMATION_CONTROLLER = "registrationConfirmationController";
    String LOGIN_CONTROLLER = "loginController";

    String WEBSITE_REGISTRATION = "website_registration";
    String WEBSITE_USER_DETAILS = "website_userDetails";

    String TRANSLATOR = "translator";

    String USERNAME_VALIDATOR = "usernameValidator";
    String PASSWORD_VALIDATOR = "passwordValidator";
    String REPEAT_PASSWORD_VALIDATOR = "repeatPasswordValidator";
    String PRENAME_VALIDATOR = "prenameValidator";
    String LASTNAME_VALIDATOR = "lastnameValidator";
    String EMAIL_VALIDATOR = "emailValidator";
    String USERNAME_OR_MAIL_VALIDATOR = "emailOrUsernameValidator";

    String USER_CONFIG_READER = "userConfigReader";

    String MAIL_CONFIG_READER = "mailConfigReader";
    String MAIL_SMTP_ADAPTER = "mailSmtpAdapter";

    String NAVIGATION_BEAN = "navigationBean";
}
