/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.util;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 25.11.2014
 *
 */
public class FileUtil
{
    public static final String WAR_NAME = "JumpUpReloaded.war";
    
    /**
     * Get the full path to the WEB-INF folder of the application
     */
    public static String getPathToWebInfFolder()
    {
        final String pathToClazz = FileUtil.class.getResource("FileUtil.class").getFile();
        
        return pathToClazz.replaceAll(WAR_NAME + ".*", "").concat(WAR_NAME + "/WEB-INF");
    }
}
