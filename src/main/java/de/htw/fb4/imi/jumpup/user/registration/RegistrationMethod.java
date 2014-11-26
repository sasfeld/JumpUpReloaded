/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.user.registration;

import javax.ejb.Local;

import de.htw.fb4.imi.jumpup.util.ErrorPrintable;

/**
 * <p>Interface for all registration methods.</p>
 * 
 * <p>It defines common steps that each registration method has to implement.</p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 10.11.2014
 *
 */
@Local
public interface RegistrationMethod extends ErrorPrintable
{

    /**
     * Main method:
     * 
     * Perform the registration of the given bean, e.g. database persistence.
     * 
     */
   void performRegistration(final RegistrationModel registrationModel);
   
   /**
    * Send an registration confirmation mail which contains the final confirmation link. 
    */
   void sendConfirmationLinkMail(final RegistrationModel registrationModel);
   
   /**
    * Send the success (final welcome) mail after the user was registered and confirmed.
    * @param registrationBean
    */
   void sendRegistrationSuccessMail(final RegistrationModel registrationModel);
   
   /**
    * Confirm the user after successful registration
    * @param registrationBean
    */
   void confirmRegistration(final RegistrationModel registrationModel);


   
   
}
