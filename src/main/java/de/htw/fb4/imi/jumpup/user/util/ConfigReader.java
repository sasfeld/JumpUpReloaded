/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.user.util;

import java.io.File;

import javax.inject.Singleton;

import de.htw.fb4.imi.jumpup.Application;
import de.htw.fb4.imi.jumpup.Application.LogType;
import de.htw.fb4.imi.jumpup.config.APropertiesConfigReader;
import de.htw.fb4.imi.jumpup.util.FileUtil;

/**
 * <p>Config Reader facade for user module.</p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 25.11.2014
 *
 */
@Singleton
public class ConfigReader extends APropertiesConfigReader
{
    protected static File CONFIG_FILE;

    static {
       // try to resolve path to user.properties file
       try {
          CONFIG_FILE = new File(FileUtil.getPathToWebInfFolder() + "/configs/user.properties");
          
          if (!CONFIG_FILE.exists()) {
              Application.log("Confg File does not exist: " + CONFIG_FILE + ". Please check the path.", LogType.CRITICAL, ConfigReader.class);
          }
          
        } catch (Exception e) {
            Application.log("Error while trying to load user.properties file.\n", LogType.CRITICAL, ConfigReader.class);
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
