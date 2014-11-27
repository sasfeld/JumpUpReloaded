/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.user.registration;

import javax.ejb.Stateless;

import de.htw.fb4.imi.jumpup.Application;
import de.htw.fb4.imi.jumpup.Application.LogType;
import de.htw.fb4.imi.jumpup.settings.BeanNames;
import de.htw.fb4.imi.jumpup.user.entities.User;

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
    /* (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.user.registration.RegistrationMethod#performRegistration(de.htw.fb4.imi.jumpup.user.registration.RegistrationBean)
     */
    @Override
    public void performRegistration(final RegistrationModel registrationModel)
    {
        this.reset();
        
        final User newUser = createAndFillUser(registrationModel);
        
        try {
            // perform logging if not entity manager is given
            if (null == entityManager) {
                Application.log(getClass() + ":performRegistration(): EntityManager is null. Please check why no entity manager is injected.", LogType.CRITICAL, getClass());
                throw new NullPointerException("entityManager is null");
            }
            
            persistInTransaction(newUser);
        } catch ( Exception e) {
            tryToRollbackAndThrow();
            throw e;
        }
    }    
}
