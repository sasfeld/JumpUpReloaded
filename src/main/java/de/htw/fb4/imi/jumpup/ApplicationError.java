/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup;

import de.htw.fb4.imi.jumpup.Application.LogType;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 27.11.2014
 *
 */
public class ApplicationError extends RuntimeException
{

    /**
     * Serialization
     */
    private static final long serialVersionUID = -1138070068838170587L;

    public ApplicationError(final String msg,
            Class<?> clazz)
    {
       super(msg);
       this.logMsg(msg, clazz);        
    }

    /**
     * Use the logging system to immediatly log the application error.
     * @param msg
     * @param clazz
     */
    private void logMsg(final String msg, final Class<?> clazz)
    {
        Application.log(msg, LogType.ERROR, clazz);        
    }


}