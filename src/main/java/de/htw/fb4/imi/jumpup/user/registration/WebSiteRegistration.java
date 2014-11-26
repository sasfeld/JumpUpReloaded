/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.user.registration;

import javax.ejb.Stateless;

import de.htw.fb4.imi.jumpup.settings.BeanNames;

/**
 * <p>Default registration via website.</p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 10.11.2014
 *
 */
@Stateless(name = BeanNames.WEBSITE_REGISTRATION)
public class WebSiteRegistration extends AbstractRegistrationMethod
{

    
}
