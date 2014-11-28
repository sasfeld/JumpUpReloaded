/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.user.registration;

import java.io.File;
import java.util.HashSet;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.htw.fb4.imi.jumpup.Application;
import de.htw.fb4.imi.jumpup.ApplicationError;
import de.htw.fb4.imi.jumpup.Application.LogType;
import de.htw.fb4.imi.jumpup.config.IConfigKeys;
import de.htw.fb4.imi.jumpup.mail.MailAdapter;
import de.htw.fb4.imi.jumpup.mail.MailModel;
import de.htw.fb4.imi.jumpup.mail.builder.MailBuilder;
import de.htw.fb4.imi.jumpup.settings.BeanNames;
import de.htw.fb4.imi.jumpup.settings.PersistenceSettings;
import de.htw.fb4.imi.jumpup.user.entities.User;
import de.htw.fb4.imi.jumpup.user.util.ConfigReader;
import de.htw.fb4.imi.jumpup.user.util.HashGenerable;
import de.htw.fb4.imi.jumpup.util.FileUtil;
import de.htw.fb4.imi.jumpup.util.StringUtil;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 10.11.2014
 *
 */
public abstract class AbstractRegistrationMethod implements RegistrationMethod
{
    protected HashSet<String> errorMessages;
    
    @Inject
    protected HashGenerable hashGenerator;
    
    @PersistenceContext(unitName = PersistenceSettings.PERSISTENCE_UNIT)
    protected EntityManager entityManager;
    
    @Inject
    protected MailBuilder mailBuilder;
    
    @EJB(beanName = BeanNames.USER_CONFIG_READER)
    protected ConfigReader userConfigReader;
    
    @Inject
    protected MailAdapter mailAdapter;   

    
    /*
     * (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.user.registration.RegistrationMethod#needsConfirmation()
     */
    public boolean needsConfirmation()
    {
        return Boolean.parseBoolean(this.userConfigReader.fetchValue(IConfigKeys.JUMPUP_USER_REGISTRATION_NEEDS_CONFIRMATION));
    }    

    protected void reset()
    {
        this.errorMessages = new HashSet<String>();
    }   

    protected void persistInTransaction(final User newUser)
    {         
        this.entityManager.persist(newUser);            
    }

    protected void tryToRollbackAndThrow()
    {
        this.errorMessages.add("Sorry, an error occured during registration. Please contact the customer care.");
        try { this.entityManager.getTransaction().rollback(); } catch ( Exception e2) {}
    }

    protected User createAndFillUser(final RegistrationModel registrationModel)
    {
        User newUser = new User();
        
        // inject hash generator
        newUser.setHashGenerable(this.hashGenerator);
        
        // fill user entity
        newUser.setEmail(registrationModel.geteMail());
        newUser.setUsername(registrationModel.getUsername());
        newUser.setPassword(registrationModel.getPassword());
        newUser.setPrename(registrationModel.getPrename());
        newUser.setLastname(registrationModel.getLastname());
        newUser.setIsConfirmed(false);
        
        return newUser;
    }

    /* (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.user.registration.RegistrationMethod#sendConfirmationLinkMail(de.htw.fb4.imi.jumpup.user.registration.RegistrationBean)
     */
    @Override
    public void sendConfirmationLinkMail(final RegistrationModel registrationModel)
    {
        // return if no confirmation is needed
        if (!this.needsConfirmation()) {
            return;
        }
        
        if (null == registrationModel.getRegisteredUser()) {
            this.errorMessages.add("I'm unable to send the confirmation link via mail. Please contact the customer care.");
            throw new ApplicationError("sendConfirmationLinkMail(): unable to send confirmation link via mail because no user instance is set. Please check why.", getClass());
        }
        
        // build mail using configured templates
        String confirmationLinkMailTxtTemplate = FileUtil.getPathToWebInfFolder() + "/" + this.userConfigReader.fetchValue(IConfigKeys.JUMPUP_USER_CONFIRMATION_MAIL_TXT_TEMPLATE);
        String confirmationLinkMailFacelet = this.userConfigReader.fetchValue(IConfigKeys.JUMPUP_USER_CONFIRMATION_MAIL_HTML_FACELET);
        
        addMailContents(confirmationLinkMailTxtTemplate, confirmationLinkMailFacelet);
        
        buildAndSendMail(registrationModel);                
    }

    protected void buildAndSendMail(RegistrationModel registrationModel)
    {
        MailModel mailModel = this.mailBuilder.getBuildedMailModel();
        try {
            mailModel.addRecipient(new InternetAddress(registrationModel.geteMail()));
            mailModel.setSender(new InternetAddress("JumpUp <test@jumpup.groupelite.de>"));        
            this.mailAdapter.sendTextMail(mailModel);
        } catch (AddressException e) {
            this.errorMessages.add("Invalid eMail adress for confirmation");
            Application.log("buildAndSendMail(): " + e.getLocalizedMessage(), LogType.ERROR, getClass());
        }      
       
        // TODO uncomment later
//        this.mailAdapter.sendHtmlMail(mailModel);
    }

    protected void addMailContents(String confirmationLinkMailTxtTemplate, String confirmationLinkMailFacelet)
    {
        this.mailBuilder.addPlainTextContent(new File(confirmationLinkMailTxtTemplate));
        // TODO uncomment later
        // this.mailBuilder.addHtmlContent(confirmationLinkMailFacelet);
    }

    /* (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.user.registration.RegistrationMethod#sendConfirmationSuccessMail(de.htw.fb4.imi.jumpup.user.registration.RegistrationBean)
     */
    @Override
    public void sendRegistrationSuccessMail(final RegistrationModel registrationModel)
    {
        // TODO implement sending of success mail

    }

    /* (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.user.registration.RegistrationMethod#confirmRegistration(de.htw.fb4.imi.jumpup.user.registration.RegistrationBean)
     */
    @Override
    public void confirmRegistration(final RegistrationModel registrationModel)
    {
        // TODO implement confirmation functionality

    }    

    @Override
    /*
     * (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.util.ErrorPrintable#hasError()
     */
    public boolean hasError()
    {
        if (null == this.errorMessages) {
            return false;
        }
        
        return this.errorMessages.size() > 0;
    }

    @Override
    /*
     * (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.util.ErrorPrintable#getErrors()
     */
    public String[] getErrors()
    {
        if (null == this.errorMessages) {
            return new String[0];
        }
        
        return this.errorMessages.toArray(new String[this.errorMessages.size()]);
    }
    
    @Override
    /*
     * (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.util.ErrorPrintable#getSingleErrorString()
     */
    public String getSingleErrorString()
    {
        if (this.hasError()) {
            return StringUtil.buildString(this.getErrors(), "<br />");
        }
        
        return "";
    }

}
