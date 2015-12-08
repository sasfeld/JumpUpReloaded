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

import de.htw.fb4.imi.jumpup.rest.AbstractRestController;
import de.htw.fb4.imi.jumpup.user.UserDAO;
import de.htw.fb4.imi.jumpup.user.entities.User;
import de.htw.fb4.imi.jumpup.user.registration.RegistrationMethod;
import de.htw.fb4.imi.jumpup.user.registration.RegistrationModel;
import de.htw.fb4.imi.jumpup.user.rest.models.UserEntityMapper;
import de.htw.fb4.imi.jumpup.user.util.IMessages;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 07.12.2015
 *
 */
public class UnsecuredBaseController extends AbstractRestController<RegistrationModel>
{
    public static final String PATH = "/user";
    private static final String PATH_PARAM_USER_ID = "userId";
    
    @Inject
    protected UserDAO userDAO;
    
    @Inject
    protected RegistrationMethod registrationMethod;
    
    protected UserEntityMapper entityMapper = new UserEntityMapper();

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
        
        return this.tryToCreateUser(restModel);      
    }

    private Response tryToCreateUser(RegistrationModel registrationModel)
    {        
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
                return this.sendCreatedResponse(this.responseEntityBuilder.buildMessageFromErrorArray(registrationMethod.getErrors()));
            } else {        
                return this.sendCreatedResponse(null);
            }
        } catch (Exception e) {
            return this.sendOkButErrorResponse(IMessages.COULD_NOT_SEND_CONFIRMATION_EMAIL);
        }
    }    
}
