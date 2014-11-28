/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.mail;

import java.util.Date;
import java.util.Properties;

import javax.ejb.Stateless;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.ws.rs.core.MediaType;

import de.htw.fb4.imi.jumpup.Application;
import de.htw.fb4.imi.jumpup.Application.LogType;
import de.htw.fb4.imi.jumpup.ApplicationError;
import de.htw.fb4.imi.jumpup.config.IConfigKeys;
import de.htw.fb4.imi.jumpup.mail.util.ConfigReader;
import de.htw.fb4.imi.jumpup.settings.BeanNames;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 28.11.2014
 *
 */
@Stateless(name = BeanNames.MAIL_SMTP_ADAPTER)
public class SmtpAdapter extends AbstractMailAdapter
{
    /**
     * 
     * <p>Mail protocol enumeration.</p>
     *
     * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
     * @since 28.11.2014
     *
     */
    public enum Protocol {
        /**
         * SMTP without SSL.
         */
        SMTP {
            /*
             * (non-Javadoc)
             * @see de.htw.fb4.imi.jumpup.mail.SmtpAdapter.Protocol#addPropertyTo(java.util.Properties)
             */
            public void addPropertyTo(final Properties mailProperties) {
                // no properties to be added
                return;
            }
        }, 
        /**
         * SMTP and SSL.
         */
        SMTPS {
            /*
             * (non-Javadoc)
             * @see de.htw.fb4.imi.jumpup.mail.SmtpAdapter.Protocol#addPropertyTo(java.util.Properties)
             */
            public void addPropertyTo(final Properties mailProperties) {
                mailProperties.put("mail.smtp.ssl.enable", true);
                return;
            }
        },
        /**
         * TLS.
         */
        TLS {
            /*
             * (non-Javadoc)
             * @see de.htw.fb4.imi.jumpup.mail.SmtpAdapter.Protocol#addPropertyTo(java.util.Properties)
             */
            public void addPropertyTo(final Properties mailProperties) {
                mailProperties.put("mail.smtp.starttls.enable", true);
                return;
            }
        };
        
        public void addPropertyTo(final Properties mailProperties) {
            throw new UnsupportedOperationException("The enumeration " + this + " does not implement addPropertyTo() yet.");
        }
    }
    
    protected String host;
    protected int port;
    protected boolean authentication;
    protected String username;
    protected String password;
    protected Protocol protocol;
    private boolean debug = false;
   

    public SmtpAdapter()
    {
        this.initialize();
    }
    
    /**
     * Constructor for unittests to suppress initialization by config reader.
     * @param supressInitialize
     */
    public SmtpAdapter(boolean suppressInitialize) 
    {
        if (!suppressInitialize) {
            this.initialize();
        }
    }
    
    public void initialize()
    {
        this.configReader = new ConfigReader();
        this.readConfig();        
    }

    /**
     * Read from configuration, assign all fields.
     */
    private void readConfig()
    {
        this.host = this.configReader.fetchValue(IConfigKeys.JUMPUP_MAIL_SMTP_HOST);
        this.port = Integer.parseInt(this.configReader.fetchValue(IConfigKeys.JUMPUP_MAIL_SMTP_PORT));
        this.username = this.configReader.fetchValue(IConfigKeys.JUMPUP_MAIL_SMTP_USERNAME);
        this.password = this.configReader.fetchValue(IConfigKeys.JUMPUP_MAIL_SMTP_PASSWORD);
        
        String protocol = this.configReader.fetchValue(IConfigKeys.JUMPUP_MAIL_SMTP_PROTOCOL);
        try {
            // Configured protocol was matched to enumeration
            this.protocol = Protocol.valueOf(protocol);
        } catch (IllegalArgumentException e) {
            // fallback to SMTP
            Application.log("readConfig(): the protocol from mail.configuration couldn't be mapped. Please correct the configuration key " + IConfigKeys.JUMPUP_MAIL_SMTP_PROTOCOL, 
                    LogType.ERROR, getClass());
            this.protocol = Protocol.SMTP;
        }        
        
        this.authentication = Boolean.parseBoolean(this.configReader.fetchValue(IConfigKeys.JUMPUP_MAIL_SMTP_AUTHENTICATION));
        this.debug = Boolean.parseBoolean(this.configReader.fetchValue(IConfigKeys.JUMPUP_MAIL_SMTP_DEBUG)); 
    }

    /* (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.mail.MailAdapter#sendMail(de.htw.fb4.imi.jumpup.mail.MailModel)
     */
    @Override
    public void sendTextMail(final MailModel mailModel)
    {
        Application.log("Trying to send text mail: " + mailModel.toString(), LogType.INFO, getClass());
        Message mailToSend = prepareMail(mailModel);
        
        try {
            mailToSend.setText(mailModel.getContentText());
            this.sendMail(mailToSend);
        } catch (MessagingException e) {
            throw new ApplicationError("Could not send text mail:\n" + e.getLocalizedMessage(), getClass());
        }
    }    
   

    @Override
    public void sendHtmlMail(MailModel mailModel)
    {
        Message mailToSend = prepareMail(mailModel);
        
        // create alternative multiparts of the same contents
        Multipart multipart = new MimeMultipart("alternative");
        
        try {
            multipart.addBodyPart(this.createHtmlPart(mailModel));
            multipart.addBodyPart(this.createTextPart(mailModel));
            mailToSend.setContent(multipart);  
            
        } catch (MessagingException e) {
            throw new ApplicationError("Could not send text mail:\n" + e.getLocalizedMessage(), getClass());
        }
        
    }

    private BodyPart createTextPart(MailModel mailModel) throws MessagingException
    {
        MimeBodyPart bodyPart = new MimeBodyPart();
        bodyPart.setText(mailModel.getContentText());
        
        return bodyPart;
    }

    private BodyPart createHtmlPart(MailModel mailModel) throws MessagingException
    {
        MimeBodyPart bodyPart = new MimeBodyPart();
        bodyPart.setContent(mailModel.getContentHtml(), MediaType.TEXT_HTML);
        
        return bodyPart;
    }

    /**
     * Prepare a mail without contents.
     * @param mailModel
     * @return
     */
    protected Message prepareMail(final MailModel mailModel)
    {
        Properties properties = this.prepareProperties();
        Authenticator authenticator = this.prepareAuthenticator();
        
        Session mailSession = this.prepareSession(properties, authenticator);
        
        Message mailToSend = this.createMail(mailSession, mailModel);
        return mailToSend;
    }        

    /**
     * Prepare the properties handed to the mail session.
     * 
     * @return
     */
    private Properties prepareProperties()
    {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", this.host);
        properties.put("mail.smtp.port", this.port);
        
        this.protocol.addPropertyTo(properties);    
        
        return properties;
    }
    
    /**
     * Prepare the authentication if it is set.
     * @return
     */
    private Authenticator prepareAuthenticator()
    {
        // return null which can be given to the session
        if (!this.authentication) {
            return null;
        }
        
        Authenticator authenticator = new Authenticator()
            {
                private PasswordAuthentication pa = new PasswordAuthentication(username, password);
                
                @Override
                public PasswordAuthentication getPasswordAuthentication() {
                    return pa;
                }
            };
        return authenticator;
    }
    
    /**
     * Prepare the mail session.
     * @param properties
     * @param authenticator
     * @return
     */
    private Session prepareSession(Properties properties, Authenticator authenticator)
    {
        Session mailSession = Session.getInstance(properties, authenticator);
        // set additional debug information on STDOUT
        mailSession.setDebug(this.debug );
        
        return mailSession;        
    }  
    
    /**
     * Create the basic mail without contents.
     * @param mailSession
     * @param mailModel
     * @return
     */
    private Message createMail(Session mailSession, MailModel mailModel)
    {
        MimeMessage message = new MimeMessage(mailSession);
        
        try {
            message.setFrom(mailModel.getSender());
            message.setRecipients(Message.RecipientType.TO, mailModel.getRecipients());
            message.setSubject(mailModel.getSubject());
            // set current date
            message.setSentDate(new Date());            
        } catch (MessagingException e) {
            throw new ApplicationError("Could not send mail:\n" + e.getLocalizedMessage(), getClass());
        }
        
        return message;
    }
    
    /**
     * Finally send the mail.
     * @param mailToSend
     * @throws MessagingException
     */
    private void sendMail(Message mailToSend) throws MessagingException
    {
        Transport.send(mailToSend);        
    } 
}
