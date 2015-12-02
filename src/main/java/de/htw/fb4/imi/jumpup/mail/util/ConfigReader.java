/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.mail.util;

import java.io.File;

import javax.ejb.Stateless;

import de.htw.fb4.imi.jumpup.Application;
import de.htw.fb4.imi.jumpup.Application.LogType;
import de.htw.fb4.imi.jumpup.config.APropertiesConfigReader;
import de.htw.fb4.imi.jumpup.settings.BeanNames;
import de.htw.fb4.imi.jumpup.util.FileUtil;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 28.11.2014
 *
 */
@Stateless(name = BeanNames.MAIL_CONFIG_READER)
public class ConfigReader extends APropertiesConfigReader
{
    protected static File MAIL_CONFIG_FILE = null;
    
    static {
     // try to resolve path to user.properties file
        try {
            MAIL_CONFIG_FILE = new File(FileUtil.getPathToWebInfFolder() + "/configs/mail.properties.local");
           
           if (!MAIL_CONFIG_FILE.exists()) {
               Application.log("Confg File does not exist: " + MAIL_CONFIG_FILE + ". Please check the path.", LogType.CRITICAL, ConfigReader.class);
           }
          
         } catch (Exception e) {
             Application.log("Error while trying to load mail.properties file.\n", LogType.CRITICAL, ConfigReader.class);
             e.printStackTrace();            
         }
    }

    public ConfigReader()
    {
        super(MAIL_CONFIG_FILE);
    }

}
