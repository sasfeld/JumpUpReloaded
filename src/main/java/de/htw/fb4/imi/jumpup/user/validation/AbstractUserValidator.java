/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.user.validation;

import javax.ejb.EJB;
import javax.inject.Inject;

import de.htw.fb4.imi.jumpup.settings.BeanNames;
import de.htw.fb4.imi.jumpup.user.login.LoginSession;
import de.htw.fb4.imi.jumpup.user.util.ConfigReader;
import de.htw.fb4.imi.jumpup.validation.validator.AbstractValidator;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 17.01.2015
 *
 */
public abstract class AbstractUserValidator extends AbstractValidator
{
    @EJB( beanName = BeanNames.USER_CONFIG_READER )
    protected ConfigReader userConfigReader;
   
    @Inject
    protected LoginSession loginSession;
    

}
