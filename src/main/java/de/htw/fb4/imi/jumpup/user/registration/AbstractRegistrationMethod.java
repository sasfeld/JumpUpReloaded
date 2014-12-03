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
import de.htw.fb4.imi.jumpup.Application.LogType;
import de.htw.fb4.imi.jumpup.ApplicationError;
import de.htw.fb4.imi.jumpup.config.IConfigKeys;
import de.htw.fb4.imi.jumpup.mail.MailAdapter;
import de.htw.fb4.imi.jumpup.mail.MailModel;
import de.htw.fb4.imi.jumpup.mail.builder.MailBuilder;
import de.htw.fb4.imi.jumpup.settings.BeanNames;
import de.htw.fb4.imi.jumpup.settings.PersistenceSettings;
import de.htw.fb4.imi.jumpup.translate.Translatable;
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
    
    @Inject
    protected Translatable translator;

    
    /*
     * (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.user.registration.RegistrationMethod#needsConfirmation()
     */
    public boolean needsConfirmation()
    {
        return Boolean.parseBoolean(this.userConfigReader.fetchValue(IConfigKeys.JUMPUP_USER_REGISTRATION_NEEDS_CONFIRMATION));
    }    

    /**
     * Reset messages.
     */
    protected void reset()
    {
        this.errorMessages = new HashSet<String>();
    }   

    /**
     * Persist in transaction.
     * @param newUser
     */
    protected void persistInTransaction(final User newUser)
    {         
        this.entityManager.persist(newUser);            
    }

    /**
     * Try to rollback if any problem occured.
     * 
     */
    protected void tryToRollbackAndThrow()
    {
        this.errorMessages.add("Sorry, an error occured during registration. Please contact the customer care.");
        try { this.entityManager.getTransaction().rollback(); } catch ( Exception e2) {}
    }

    /**
     * Create the user entity.
     * @param registrationModel
     * @return
     */
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
        
        // set is confirmed flag
        if (this.needsConfirmation()) {
            newUser.setIsConfirmed(false);
        } else {
            newUser.setIsConfirmed(true);
        }
        
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
        
        // inject hash generable
        registrationModel.setHashGenerable(this.hashGenerator);
        
        // build mail using configured templates
        String confirmationLinkMailTxtTemplate = FileUtil.getPathToWebInfFolder() + "/" + this.userConfigReader.fetchValue(IConfigKeys.JUMPUP_USER_CONFIRMATION_MAIL_TXT_TEMPLATE);
        String confirmationLinkMailFacelet = this.userConfigReader.fetchValue(IConfigKeys.JUMPUP_USER_CONFIRMATION_MAIL_HTML_FACELET);
        
        addMailContents(confirmationLinkMailTxtTemplate, confirmationLinkMailFacelet);
        
        MailModel mailModel = buildMail(registrationModel); 
        
        // set subject
        mailModel.setSubject(this.translator.translate("Welcome to JumpUp.Me - please confirm your registration"));
        
        this.mailAdapter.sendHtmlMail(mailModel);
    }

    /**
     * Build general mail, add the recipient by the registration model.
     * @param registrationModel
     * @return
     */
    protected MailModel buildMail(RegistrationModel registrationModel)
    {
        MailModel mailModel = this.mailBuilder.getBuildedMailModel();
        try {
            // recipient: registered user
            Application.log(registrationModel.getRegisteredUser() + "", LogType.INFO, getClass());
            mailModel.addRecipient(new InternetAddress(registrationModel.getRegisteredUser().geteMail()));
            // sender: defined in user.config
            mailModel.setSender(new InternetAddress(this.userConfigReader.fetchValue(IConfigKeys.JUMPUP_USER_CONFIRMATION_MAIL_SENDER)));        
            return mailModel;
        } catch (AddressException e) {
            this.errorMessages.add("Invalid eMail adress for confirmation");
            throw new ApplicationError("buildAndSendMail(): " + e.getLocalizedMessage(), getClass());
        }      
    }

    /**
     * Add mail contents, both plain text and the facelet link.
     * @param confirmationLinkMailTxtTemplate path to the txt template
     * @param confirmationLinkMailFacelet as if you would call it in your browser.
     */
    protected void addMailContents(String confirmationLinkMailTxtTemplate, String confirmationLinkMailFacelet)
    {
        this.mailBuilder.addPlainTextContent(new File(confirmationLinkMailTxtTemplate));
        this.mailBuilder.addHtmlContent(confirmationLinkMailFacelet);
    }

    /* (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.user.registration.RegistrationMethod#sendConfirmationSuccessMail(de.htw.fb4.imi.jumpup.user.registration.RegistrationBean)
     */
    @Override
    public void sendRegistrationSuccessMail(final RegistrationModel registrationModel)
    {
        if (null == registrationModel.getRegisteredUser()) {
            throw new ApplicationError("sendRegistrationSuccessMail(): no user entity was attached to the registration model. Make sure that it is set anywhere.", getClass());
        }
        
        // build mail using configured templates
        String confirmationLinkMailTxtTemplate = FileUtil.getPathToWebInfFolder() + "/" + this.userConfigReader.fetchValue(IConfigKeys.JUMPUP_USER_REGISTRATION_SUCCESS_MAIL_TXT_TEMPLATE);
        String confirmationLinkMailFacelet = this.userConfigReader.fetchValue(IConfigKeys.JUMPUP_USER_REGISTRATION_SUCCESS_MAIL_HTML_FACELET);
        
        addMailContents(confirmationLinkMailTxtTemplate, confirmationLinkMailFacelet);
        
        MailModel mailModel = buildMail(registrationModel); 
        
        // set success subject
        mailModel.setSubject(this.translator.translate("Welcome to JumpUp.Me, " + registrationModel.getRegisteredUser().getPrename() + ", you were successfully registered!" ));
        
        this.mailAdapter.sendHtmlMail(mailModel);
    }

    /* (non-Javadoc)
     * @see de.htw.fb4.imi.jumpup.user.registration.RegistrationMethod#confirmRegistration(de.htw.fb4.imi.jumpup.user.registration.RegistrationBean)
     */
    @Override
    public void confirmRegistration(final RegistrationModel registrationModel)
    {
        this.reset();
        
        User matchingUser = this.findMatchingUser(registrationModel.getUsername());
        
        Application.log(registrationModel.toString(), LogType.INFO, getClass());
        
        // no user found
        if (null == matchingUser) {
            this.errorMessages.add("Could not find the user. Please check the confirmation link - it might be broken.");
            return;
        }
        
        // confirmation hash is invalid
        if (!this.checkConfirmationHash(registrationModel, matchingUser)) {
            this.errorMessages.add("We couldn't confirm you. Please make sure, that the link is correct and not broken.");
            return;
        }
        
        // everything is ok, so confirm the user
        registrationModel.setRegisteredUser(matchingUser);
        this.confirmAndPersistUser(matchingUser);
    }    

    /**
     * Set user confirmed and persist.
     * @param matchingUser
     */
    private void confirmAndPersistUser(final User matchingUser)
    {
        matchingUser.setIsConfirmed(true);
        
        try {
            this.persistInTransaction(matchingUser);
        } catch (Exception e) {
            this.tryToRollbackAndThrow();
        }        
    }

    /**
     * Check if the provided confirmation hash equals the one of the fetched {@link User} instance.
     * 
     * @param registrationModel contains the confirmation hash
     * @param matchingUser
     * @return
     */
    private boolean checkConfirmationHash(final RegistrationModel registrationModel,
            final User matchingUser)
    {        
        String usernameHash = new String(this.hashGenerator.generateHash(matchingUser.getUsername()));
        Application.log("UsernameHash: " + usernameHash, LogType.INFO, getClass());
        Application.log("getHashForConfirmation: " + registrationModel.getHashForConfirmation(), LogType.INFO, getClass());
            
        return usernameHash.equals(registrationModel.getHashForConfirmation());
    }

    /**
     * 
     * @param username
     * @return null or the matched {@link User}
     */
    private User findMatchingUser(final String username)
    {
        try {
            // perform logging if not entity manager is given
            if (null == entityManager) {
                Application.log(getClass() + ":findMatchingUser(): EntityManager is null. Please check why no entity manager is injected.", LogType.CRITICAL, getClass());
                throw new NullPointerException("entityManager is null");
            }
            
            return (User) this.entityManager.createNamedQuery(User.NAME_QUERY_BY_USERNAME)
                    .setParameter("username", username).getSingleResult();
        } catch ( Exception e) {
           return null;
        }
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
