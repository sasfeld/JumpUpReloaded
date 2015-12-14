/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.user.rest;

import javax.ejb.EJBException;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.htw.fb4.imi.jumpup.rest.controller.AbstractRestController;
import de.htw.fb4.imi.jumpup.user.UserDAO;
import de.htw.fb4.imi.jumpup.user.entity.User;
import de.htw.fb4.imi.jumpup.user.registration.RegistrationMethod;
import de.htw.fb4.imi.jumpup.user.registration.RegistrationModel;
import de.htw.fb4.imi.jumpup.user.registration.RegistrationSession;
import de.htw.fb4.imi.jumpup.user.rest.model.UserEntityMapper;
import de.htw.fb4.imi.jumpup.user.util.IMessages;
import de.htw.fb4.imi.jumpup.validation.ValidationException;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 07.12.2015
 *
 */
public class UnsecuredBaseController extends AbstractRestController<RegistrationModel>
{
    public static final String PATH = "/public/user";
    private static final String PATH_PARAM_USER_ID = "userId";
    private static final String ENTITY_NAME = "user";
    
    @Inject
    protected UserDAO userDAO;
    @Inject
    protected RegistrationMethod registrationMethod;
    
    @Inject
    protected RegistrationSession registrationSession;
    
    @Inject
    protected UserEntityMapper entityMapper;

    @GET
    @Path("{" + PATH_PARAM_USER_ID + "}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@Context HttpHeaders headers, @PathParam(PATH_PARAM_USER_ID) Long entityId){
        Response response = super.get(headers);
        
        if (null != response) {
            return response;
        }
        
        return this.tryToLoadUser(entityId);
    }

    private Response tryToLoadUser(Long entityId)
    {
        try {
            User user = this.userDAO.loadById(entityId);
            
            return Response
                    .ok(entityMapper.mapEntity(user))
                    .build();
        } catch (EJBException e) {
            if (e.getCausedByException() instanceof NoResultException) {
                return this.sendNotFoundResponse(String.format(IMessages.NO_USER_WITH_ID, entityId));
            } else {
                throw e;
            }    
        }
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response post(@Context HttpHeaders headers, RegistrationModel restModel) {
        Response response = super.post(headers, restModel);
        
        if (null != response) {
            return response;
        }        
        
        try {
            entityMapper.validateRegistration(restModel);
        } catch (ValidationException e) {
            return this.sendBadRequestResponse(e);
        }
        
        return this.tryToCreateUser(restModel);      
    }

    private Response tryToCreateUser(RegistrationModel registrationModel)
    {        
        // set unmarshalled registration model in registration session so that other services such as mails can be triggered
        registrationSession.setRegistrationModel(registrationModel);
        
        registrationMethod.performRegistration(registrationModel);
        
        if (registrationMethod.hasError()) {
            return this.sendInternalServerErrorResponse(registrationMethod);
        }
        
        try {
            if (registrationMethod.needsConfirmation()) {
                registrationMethod.sendConfirmationLinkMail(registrationModel);
            } else {
                registrationMethod.sendRegistrationSuccessMail(registrationModel);
            }
            
            if (registrationMethod.hasError()) {
                return this.sendOkButErrorResponse(registrationMethod.getSingleErrorString());
            } else {                     
                return this.sendCreatedResponse(ENTITY_NAME, registrationSession.getRegistrationModel().getRegisteredUser().getIdentity());
                
            }
        } catch (Exception e) {
            return this.sendOkButErrorResponse(IMessages.COULD_NOT_SEND_CONFIRMATION_EMAIL);
        }
    }    
}
