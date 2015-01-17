/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.trip.util;

import java.io.File;

import de.htw.fb4.imi.jumpup.Application;
import de.htw.fb4.imi.jumpup.Application.LogType;
import de.htw.fb4.imi.jumpup.config.APropertiesConfigReader;
import de.htw.fb4.imi.jumpup.util.FileUtil;

/**
 * <p>Config reader for trip module.</p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 17.01.2015
 *
 */
public class ConfigReader extends APropertiesConfigReader
{
    protected static File CONFIG_FILE;

    static {
       // try to resolve path to user.properties file
       try {
          CONFIG_FILE = new File(FileUtil.getPathToWebInfFolder() + "/configs/trip.properties");
          
          if (!CONFIG_FILE.exists()) {
              Application.log("Confg File does not exist: " + CONFIG_FILE + ". Please check the path.", LogType.CRITICAL, ConfigReader.class);
          }
          
        } catch (Exception e) {
            Application.log("Error while trying to load trip.properties file.\n", LogType.CRITICAL, ConfigReader.class);
            e.printStackTrace();            
        }
    }
    
    /**
     * Create the config reader for the user module.
     */
    public ConfigReader()
    {
        super(CONFIG_FILE);
    }
}
