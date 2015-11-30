/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.trip.rest;

import javax.ejb.EJBException;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.htw.fb4.imi.jumpup.rest.SecuredRestController;
import de.htw.fb4.imi.jumpup.trip.TripDAO;
import de.htw.fb4.imi.jumpup.trip.creation.TripManagementMethod;
import de.htw.fb4.imi.jumpup.trip.entities.Trip;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 25.11.2015
 *
 */
public class BaseController extends SecuredRestController
{
    private static final String PATH_PARAM_TRIP_ID = "tripId";
    
    @Inject
    protected TripDAO tripDAO;
    
    @Inject
    protected TripManagementMethod tripManagement;

    @GET
    @Path("{" + PATH_PARAM_TRIP_ID + "}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@Context HttpHeaders headers, @PathParam(PATH_PARAM_TRIP_ID) Long entityId){
        Response response = super.get(headers);
        
        if (null != response) {
            return response;
        }
        
        return this.tryToLoadTrip(entityId);
    }
    
    private Response tryToLoadTrip(Long entityId)
    {
        try {
            Trip trip = this.tripDAO.getTripByID(entityId);
            
            return Response
                    .ok(trip)
                    .build();
        } catch (EJBException e) {
            if (e.getCausedByException() instanceof NoResultException) {
                return this.sendNotFoundResponse();
            } else {
                throw e;
            }    
        }
    }

    @DELETE
    @Path("{" + PATH_PARAM_TRIP_ID + "}")
    public Response delete(@Context HttpHeaders headers, @PathParam(PATH_PARAM_TRIP_ID) Long entityId){
        Response response = super.get(headers);
        
        if (null != response) {
            return response;
        }
        
        return this.tryToLoadTripAuthorizeAndDelete(entityId);
    }

    private Response tryToLoadTripAuthorizeAndDelete(Long entityId)
    {
        try {
            Trip trip = this.tripDAO.getTripByID(entityId);
            
            Response authorizeResponse = authorizeForTrip(trip);
            if (null != authorizeResponse) {
                // not authorized
                return authorizeResponse;
            }
            
            // try to cancel trip
            this.tripManagement.cancelTrip(trip);
            
            if (this.tripManagement.hasError()) {
                return this.sendInternalServerErrorResponse(this.tripManagement);
            }
            
            return this.sendOkResponse("Cancelled trip with ID " + trip.getIdentity());
        } catch (EJBException e) {
            if (e.getCausedByException() instanceof NoResultException) {
                return this.sendNotFoundResponse();
            } else {
                throw e;
            }            
        }
    }

    protected Response authorizeForTrip(Trip trip)
    {
        if (trip.getDriver().getIdentity() != this.getLoginModel().getCurrentUser().getIdentity()) {
            return this.sendForbiddenResponse();
        }
        
        return null;
    }
}
