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
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.htw.fb4.imi.jumpup.rest.controller.SecuredRestController;
import de.htw.fb4.imi.jumpup.user.UserDAO;
import de.htw.fb4.imi.jumpup.user.details.UserDetailsMethod;
import de.htw.fb4.imi.jumpup.user.entity.User;
import de.htw.fb4.imi.jumpup.user.rest.model.UserEntityMapper;
import de.htw.fb4.imi.jumpup.user.rest.model.UserWebServiceModel;
import de.htw.fb4.imi.jumpup.user.util.IMessages;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 07.12.2015
 *
 */
public class SecuredBaseController extends SecuredRestController<UserWebServiceModel>
{
    public static final String PATH = "/user";
    private static final String PATH_PARAM_USER_ID = "userId";
    
    protected UserEntityMapper entityMapper = new UserEntityMapper();
    
    @Inject
    protected UserDAO userDAO;
    
    @Inject
    protected UserDetailsMethod userDetailsMethod;
    
    @PUT
    @Path("{" + PATH_PARAM_USER_ID + "}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response put(@Context HttpHeaders headers, @PathParam(PATH_PARAM_USER_ID) Long entityId, UserWebServiceModel restModel) {
        Response response = super.put(headers, entityId, restModel);
        
        if (null != response) {
            return response;
        }
        
        User user = entityMapper.mapWebServiceModel(restModel);
        
        return this.tryToUpdateUser(entityId, user);      
    }

    private Response tryToUpdateUser(Long entityId, User user)
    {
        try {
            User loadedUser = this.userDAO.loadById(entityId);
            
            // user exists -> authorize
            Response authorizeResponse = authorizeForUser(loadedUser);
            
            if (null != authorizeResponse) {
                // not authorized
                return authorizeResponse;
            }
            
            // authorized -> update user details
            user.setIdentity(loadedUser.getIdentity());
            
            Long detailsIdentity = (loadedUser.getUserDetails() != null ? loadedUser.getUserDetails().getIdentity() : null);
            
            if (null != detailsIdentity) {
                user.getUserDetails().setIdentity(detailsIdentity);
            }
            
            UserDetailsMethod userDetailsMethod = getUserDetailsMethod();
            userDetailsMethod.sendUserDetails(user.getUserDetails());
            
            if (userDetailsMethod.hasError()) {
                return this.sendInternalServerErrorResponse(userDetailsMethod);
            }
            
            return this.sendOkResponse("User with ID " + entityId + " was successfully updated!");         
        } catch (EJBException e) {
            if (e.getCausedByException() instanceof NoResultException) {
                return this.sendNotFoundResponse(String.format(IMessages.NO_USER_WITH_ID, entityId));
            } else {
                throw e;
            }    
        }
    }

    protected UserDetailsMethod getUserDetailsMethod()
    {
        return userDetailsMethod;
    }

    private Response authorizeForUser(User loadedUser)
    {
        if (loadedUser.getIdentity() != this.getLoginModel().getCurrentUser().getIdentity()) {
            return this.sendForbiddenResponse();
        }
        
        return null;
    }    
}
