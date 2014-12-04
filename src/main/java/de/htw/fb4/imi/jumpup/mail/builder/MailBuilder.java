/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.mail.builder;

import java.io.File;

import javax.ejb.Local;

import de.htw.fb4.imi.jumpup.mail.MailModel;

/**
 * <p>The MailBuilder is responsible for building {@link MailModel} by template files.</p>
 * 
 * <p>For example: You can hand in an *.txt file containing expression language (EL) expressions. Those will be evaluated and set as content of the mail model.</p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 28.11.2014
 *
 */
@Local
public interface MailBuilder
{
    /**
     * <p>Add plain text content by the given template file.</p>
     * 
     * <p>The template file can be a *.txt for example.</p>
     * 
     * @param templateFile
     * @return
     */
    MailBuilder addPlainTextContent(final File templateFile);
    
    /**
     * <p>Add HTML text content by the given template file.</p>
     * 
     * <p>The templat efile can be a .*xhtml for example.</p>
     * 
     * @param faceletPath the path to the facelet as if you would call it in your browser
     * @return
     */
    MailBuilder addHtmlContent(final String faceletPath);
    
    /**
     * <p>Finally get the buildet mail model.</p>
     * @return
     */
    MailModel getBuildedMailModel();
}
