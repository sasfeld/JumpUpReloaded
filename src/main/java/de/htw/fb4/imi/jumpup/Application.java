/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup;

import java.util.HashMap;
import java.util.Map;

import org.jboss.logging.Logger;

/**
 * <p>The central static facade of our application.</p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 24.11.2014
 *
 */
public class Application
{
    /**
     * 
     * <p>The LogType enumeration follows the strategy by implementing a log() method for each strategy.</p>
     *
     * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
     * @since 24.11.2014
     *
     */
    public enum LogType {
        INFO() 
        {
            @Override
            /*
             * (non-Javadoc)
             * @see de.htw.fb4.imi.jumpup.Application.LogType#log(java.lang.String, org.jboss.logging.Logger)
             */
            public <T> void log(final String message, final Logger jBossLogger)
            {
                jBossLogger.info(message);
            }
        }, 
        
        WARNING
        {
            @Override
            /*
             * (non-Javadoc)
             * @see de.htw.fb4.imi.jumpup.Application.LogType#log(java.lang.String, org.jboss.logging.Logger)
             */
            public <T> void log(final String message, final Logger jBossLogger)
            {
                jBossLogger.debug(message);
            }
        }, 
        
        ERROR 
        {
            @Override
            /*
             * (non-Javadoc)
             * @see de.htw.fb4.imi.jumpup.Application.LogType#log(java.lang.String, org.jboss.logging.Logger)
             */
            public <T> void log(final String message, final Logger jBossLogger)
            {
                jBossLogger.error(message);
            }
        }, 
        
        CRITICAL
        {
            @Override
            /*
             * (non-Javadoc)
             * @see de.htw.fb4.imi.jumpup.Application.LogType#log(java.lang.String, org.jboss.logging.Logger)
             */
            public <T> void log(final String message, final Logger jBossLogger)
            {
                jBossLogger.fatal(message);
            }
        };

        /**
         * Log the given message using the given JBoss Logger.
         * @param message
         * @param jBossLogger
         * @throws UnsupportedOperationException
         */
        public <T> void log(final String message, final Logger jBossLogger)
        {
            throw new UnsupportedOperationException("The enumeration value " + this + " doesn't implement the method log(). Please implement it!");
        }
    }
    
    /**
     * JBoss Loggers attached to the Java classes.
     */
    protected static Map<Class<?>, Logger> jBossLoggers = new HashMap<>();  
    
   
    /**
     * Central facade method for using our logging system / adaption.
     * 
     * @param message
     * @param logType
     * @param clazz
     */
    public static <T> void log(final String message, final LogType logType, final Class<T> clazz) 
    {
        addLoggerIfNotYetDone(clazz);
        
        // Log using JBoss
        logType.log(message, jBossLoggers.get(clazz));
    }
    
    /**
     * Add logger being lazy.
     * @param clazz
     */
    protected static <T> void addLoggerIfNotYetDone(final Class<T> clazz)
    {
        if (!jBossLoggers.containsKey(clazz)) {
            jBossLoggers.put(clazz, Logger.getLogger(clazz));
        }
    }
}
