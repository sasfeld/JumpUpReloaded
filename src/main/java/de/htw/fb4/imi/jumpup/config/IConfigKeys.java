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
    String JUMUP_USER_VALIDATION_PASSWORD_MIN_LENGTH = "jumupup.user.validation.password.min_length";
    String JUMUP_USER_VALIDATION_PASSWORD_MAX_LENGTH = "jumupup.user.validation.password.max_length";
    String JUMUP_USER_VALIDATION_PRENAME_MIN_LENGTH = "jumupup.user.validation.prename.min_length";
    String JUMUP_USER_VALIDATION_PRENAME_MAX_LENGTH = "jumupup.user.validation.prename.max_length";
    String JUMUP_USER_VALIDATION_LASTNAME_MIN_LENGTH = "jumupup.user.validation.lastname.min_length";
    String JUMUP_USER_VALIDATION_LASTNAME_MAX_LENGTH = "jumupup.user.validation.lastname.max_length";
    
}
