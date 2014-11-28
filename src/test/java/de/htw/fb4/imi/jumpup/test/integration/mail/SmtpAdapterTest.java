/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.test.integration.mail;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.junit.Test;

import de.htw.fb4.imi.jumpup.mail.MailModel;
import de.htw.fb4.imi.jumpup.mail.SmtpAdapter;
import de.htw.fb4.imi.jumpup.test.MailConfigReader;

/**
 * <p>Integration test of the mailing system.</p>
 * 
 * <p>This test requires a config file src/test/resources/config/mail.properties.local .</p>
 * 
 * <p>Set the jumpup.test.enable_integration in unittest.properties to 'true' to activate this test.</p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 28.11.2014
 *
 */
public class SmtpAdapterTest extends de.htw.fb4.imi.jumpup.test.IntegrationTest
{
    @Test
    public void testSendMailText() throws AddressException
    {
        if (!this.enabled) {
            return;
        }
        
        MailModel model = this.givenAnPlainTextMail();
        
        // when plain mail is sent
        this.whenPlainMailIsSent(model);    
        
        // no exception is thrown, so everything is ok
    }  

    private MailModel givenAnPlainTextMail() throws AddressException
    {
        MailModel mailModel = new MailModel();
        
        mailModel.setContentText("Hallo.\n\n Das ist ein Test unseres eMail-Adapters.\n\nWenn du diese Mail liest, hat er funktioniert");
        mailModel.setSender(new InternetAddress(this.configReader.fetchValue("jumpup.test.mail.sender")));
        mailModel.addRecipient(new InternetAddress(this.configReader.fetchValue("jumpup.test.mail.recipient")));
        
        return mailModel;
    }
    
    private void whenPlainMailIsSent(MailModel model)
    {
        SmtpAdapter smtpAdapter = new SmtpAdapter(true);
        smtpAdapter.setConfigReader(new MailConfigReader());
        smtpAdapter.initialize();
        
        // send mail
        smtpAdapter.sendTextMail(model);
    }

}
