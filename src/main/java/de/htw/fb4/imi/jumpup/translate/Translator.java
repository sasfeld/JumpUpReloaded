/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.translate;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import de.htw.fb4.imi.jumpup.settings.BeanNames;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 12.11.2014
 *
 */
@ManagedBean(name=BeanNames.TRANSLATOR)
@ApplicationScoped
public class Translator implements Translatable
{
    /*
     * (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.translate.Translatable#translate(java.lang.String)
     */
    public String translate(final String message) 
    {
        /*
         * TODO trigger translation service from here.
         */
        return message;
    }
}