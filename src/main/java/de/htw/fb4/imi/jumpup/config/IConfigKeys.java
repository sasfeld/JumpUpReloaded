/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.config;

/**
 * <p>
 * This interface stores the keys of the configuration target (e.g.: a file) in
 * an unique way.
 * </p>
 * 
 * <p>
 * This interface only contains constants.
 * </p>
 * 
 * @author <a href="mailto:sascha.feldmann@gmx.de">Sascha Feldmann</a>
 * @since 13.11.2013
 * 
 */
public interface IConfigKeys {
   /*
    * user module
    */
    String JUMPUP_USER_VALIDATION_PASSWORD_MIN_LENGTH = "jumupup.user.validation.password.min_length";
    String JUMPUP_USER_VALIDATION_PASSWORD_MAX_LENGTH = "jumupup.user.validation.password.max_length";
    String JUMPUP_USER_VALIDATION_PRENAME_MIN_LENGTH = "jumupup.user.validation.prename.min_length";
    String JUMPUP_USER_VALIDATION_PRENAME_MAX_LENGTH = "jumupup.user.validation.prename.max_length";
    String JUMPUP_USER_VALIDATION_LASTNAME_MIN_LENGTH = "jumupup.user.validation.lastname.min_length";
    String JUMPUP_USER_VALIDATION_LASTNAME_MAX_LENGTH = "jumupup.user.validation.lastname.max_length";
    String JUMPUP_USER_VALIDATION_USERNAME_MIN_LENGTH = "jumupup.user.validation.username.min_length";
    String JUMPUP_USER_VALIDATION_USERNAME_MAX_LENGTH = "jumupup.user.validation.username.max_length";
    String JUMPUP_USER_REGISTRATION_NEEDS_CONFIRMATION = "jumpup.user.registration.needs_confirmation";
    String JUMPUP_USER_CONFIRMATION_MAIL_TXT_TEMPLATE = "jumpup.user.confirmation.mail.template.txt";
    String JUMPUP_USER_CONFIRMATION_MAIL_HTML_FACELET = "jumpup.user.confirmation.mail.template.html_facelet";
    String JUMPUP_USER_CONFIRMATION_MAIL_SENDER = "jumpup.user.confirmation.mail.sender";
    String JUMPUP_USER_REGISTRATION_SUCCESS_MAIL_TXT_TEMPLATE = "jumpup.user.registration_success.mail.template.txt";
    String JUMPUP_USER_REGISTRATION_SUCCESS_MAIL_HTML_FACELET = "jumpup.user.registration_success.mail.template.html_facelet";
    
    /*
     * mail module
     */
    String JUMPUP_MAIL_SMTP_HOST = "jumpup.mail.smtp.host";
    String JUMPUP_MAIL_SMTP_PORT = "jumpup.mail.smtp.port";
    String JUMPUP_MAIL_SMTP_AUTHENTICATION = "jumpup.mail.smtp.authentication";
    String JUMPUP_MAIL_SMTP_USERNAME = "jumpup.mail.smtp.username";
    String JUMPUP_MAIL_SMTP_PASSWORD = "jumpup.mail.smtp.password";
    String JUMPUP_MAIL_SMTP_PROTOCOL = "jumpup.mail.smtp.protocol";
    String JUMPUP_MAIL_SMTP_DEBUG = "jumpup.mail.smtp.debug";   

}
