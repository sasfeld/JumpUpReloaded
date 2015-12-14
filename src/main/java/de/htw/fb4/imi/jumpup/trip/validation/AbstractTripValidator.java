/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.trip.validation;

import javax.ejb.EJB;

import de.htw.fb4.imi.jumpup.settings.BeanNames;
import de.htw.fb4.imi.jumpup.trip.util.ConfigReader;
import de.htw.fb4.imi.jumpup.validation.validator.AbstractValidator;

/**
 * <p>Abstract validators within for all validators in the trip module.</p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 17.01.2015
 *
 */
public abstract class AbstractTripValidator extends AbstractValidator
{
    @EJB( beanName = BeanNames.TRIP_CONFIG_READER )
    protected ConfigReader tripConfigReader;
  
}
