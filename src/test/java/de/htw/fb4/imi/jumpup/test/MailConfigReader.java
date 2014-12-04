/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.test;

import java.io.File;

import de.htw.fb4.imi.jumpup.config.APropertiesConfigReader;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 28.11.2014
 *
 */
public class MailConfigReader extends APropertiesConfigReader
{
    private static final File MAIL_PROPERTIES_FILE = new File("./src/test/resources/config/mail.properties.local");

    static {
        if (!MAIL_PROPERTIES_FILE.exists()) {
            System.err.println("Could not load " + MAIL_PROPERTIES_FILE.getAbsolutePath());
        }
    }
    
    public MailConfigReader()
    {
        super(MAIL_PROPERTIES_FILE);
    }
}
