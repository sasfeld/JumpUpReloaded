/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.trip.rest;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ejb.EJBException;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.htw.fb4.imi.jumpup.rest.BasicRestController;
import de.htw.fb4.imi.jumpup.rest.SecuredRestController;
import de.htw.fb4.imi.jumpup.trip.TripDAO;
import de.htw.fb4.imi.jumpup.trip.creation.TripManagementMethod;
import de.htw.fb4.imi.jumpup.trip.entities.Trip;
import de.htw.fb4.imi.jumpup.trip.rest.models.EntityMapper;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 25.11.2015
 *
 */
public class BaseController extends SecuredRestController<Trip>
{
    public static final String PATH = "/trip";
    private static final String PATH_PARAM_TRIP_ID = "tripId";
    
    @Inject
    protected TripDAO tripDAO;
    
    @Inject
    protected TripManagementMethod tripManagement;
    
    protected EntityMapper entityMapper = new EntityMapper();

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
                    .ok(entityMapper.mapEntity(trip))
                    .build();
        } catch (EJBException e) {
            if (e.getCausedByException() instanceof NoResultException) {
                return this.sendNotFoundResponse();
            } else {
                throw e;
            }    
        }
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response post(@Context HttpHeaders headers, de.htw.fb4.imi.jumpup.trip.rest.models.Trip restModel) {
        Response response = super.get(headers);
        
        if (null != response) {
            return response;
        }
        
        Trip trip = (Trip) entityMapper.mapWebServiceModel(restModel);
        
        return this.tryToCreateTrip(trip);      
    }

    private Response tryToCreateTrip(Trip trip)
    {
        this.tripManagement.addTrip(trip);
        
        if (this.tripManagement.hasError()) {
            return this.sendInternalServerErrorResponse(this.tripManagement);
        }
        
        return this.sendCreatedResponse(this.getGetUrl(trip));
    }

    private URI getGetUrl(Trip trip)
    {
        try {
            return new URI(BasicRestController.BASE_PATH + PATH + "/" + trip.getIdentity());
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
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
