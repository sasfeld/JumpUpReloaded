/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.mail;

import javax.ejb.EJB;

import de.htw.fb4.imi.jumpup.mail.util.ConfigReader;
import de.htw.fb4.imi.jumpup.settings.BeanNames;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 28.11.2014
 *
 */
public abstract class AbstractMailAdapter implements MailAdapter
{
    @EJB(beanName = BeanNames.MAIL_CONFIG_READER)
    protected ConfigReader configReader;

    /**
     * @param configReader the configReader to set
     */
    public void setConfigReader(ConfigReader configReader)
    {
        this.configReader = configReader;
    }   
    
}
