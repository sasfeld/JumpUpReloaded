/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.mail;

import javax.ejb.Local;

import de.htw.fb4.imi.jumpup.ApplicationError;

/**
 * <p>Interface for all mail adapter implementations.</p>
 * 
 * <p>Use the interface if you want to send an e-mail anywhere in the application.</p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 28.11.2014
 *
 */
@Local
public interface MailAdapter
{
    /**
     * Send a plain text eMail using a {@link MailModel} object.
     * 
     * @param mailModel
     * @throws ApplicationError
     */
    void sendTextMail(final MailModel mailModel);
    
    /**
     * Send an HTML mail. Make sure that mail model contains both text and HTML contents in case of fallback.
     * @param mailModel
     * @throws ApplicationError
     */
    void sendHtmlMail(final MailModel mailModel);
}
