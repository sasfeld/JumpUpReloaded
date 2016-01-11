/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.translate;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import de.htw.fb4.imi.jumpup.settings.BeanNames;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 12.11.2014
 *
 */
@Named(value=BeanNames.TRANSLATOR)
@SessionScoped
public class Translator implements Translatable, Serializable
{    
    /**
     * 
     */
    private static final long serialVersionUID = 5577524187193929751L;

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
