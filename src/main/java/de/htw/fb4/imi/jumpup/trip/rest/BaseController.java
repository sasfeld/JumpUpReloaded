/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.trip.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ejb.EJBException;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
import de.htw.fb4.imi.jumpup.trip.rest.models.TripEntityMapper;
import de.htw.fb4.imi.jumpup.trip.rest.models.TripWebServiceModel;
import de.htw.fb4.imi.jumpup.trip.util.IMessages;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 25.11.2015
 *
 */
public class BaseController extends SecuredRestController<TripWebServiceModel>
{
    public static final String PATH = "/trip";
    private static final String PATH_PARAM_TRIP_ID = "tripId";
    
    @Inject
    protected TripDAO tripDAO;
    
    @Inject
    protected TripManagementMethod tripManagement;   
    
    protected TripEntityMapper entityMapper = new TripEntityMapper();

    private TripManagementMethod getTripManagementMethod()
    {
        this.tripManagement.setLoginModel(getLoginModel());
        
        return this.tripManagement;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@Context HttpHeaders headers) {
        Response response = super.get(headers);
        
        if (null != response) {
            return response;
        }
        
        return this.tryToLoadUsersTrips();
    }   
    
    private Response tryToLoadUsersTrips()
    {
        List<Trip> offeredTrips = this.tripDAO.getOfferedTrips(getLoginModel().getCurrentUser());
        
        if (null == offeredTrips) {
            return this.sendNotFoundResponse(this.translator.translate(""));
        }        
        
        return Response
                .ok(entityMapper.mapEntities(offeredTrips))
                .build();
    }

    @GET
    @Path("{" + PATH_PARAM_TRIP_ID + "}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@Context HttpHeaders headers, @PathParam(PATH_PARAM_TRIP_ID) Long entityId){
        Response response = super.get(headers, entityId);
        
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
                return this.sendNotFoundResponse(translator.translate(IMessages.NO_TRIPS_YET));
            } else {
                throw e;
            }    
        }
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response post(@Context HttpHeaders headers, TripWebServiceModel restModel) {
        Response response = super.post(headers, restModel);
        
        if (null != response) {
            return response;
        }
        
        Trip trip = (Trip) entityMapper.mapWebServiceModel(restModel);
        
        return this.tryToCreateTrip(trip);      
    }

    private Response tryToCreateTrip(Trip trip)
    {
        TripManagementMethod tripManagementMethod = this.getTripManagementMethod();
        
        tripManagementMethod.addTrip(trip);
        
        if (tripManagementMethod.hasError()) {
            return this.sendInternalServerErrorResponse(tripManagementMethod);
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
    
    @PUT
    @Path("{" + PATH_PARAM_TRIP_ID + "}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response put(@Context HttpHeaders headers, @PathParam(PATH_PARAM_TRIP_ID) Long entityId, TripWebServiceModel restModel) {
        Response response = super.put(headers, entityId, restModel);
        
        if (null != response) {
            return response;
        }
        
        Trip trip = (Trip) entityMapper.mapWebServiceModel(restModel);
        
        return this.tryToUpdateTrip(entityId, trip);      
    }

    private Response tryToUpdateTrip(Long entityId, Trip trip)
    {
        try {
            Trip loadedTrip = this.tripDAO.getTripByID(entityId);
            
            // trip exists -> authorize
            Response authorizeResponse = authorizeForTrip(loadedTrip);
            
            if (null != authorizeResponse) {
                // not authorized
                return authorizeResponse;
            }
            
            // authorized -> update trip
            trip.setIdentity(loadedTrip.getIdentity());
            TripManagementMethod tripManagementMethod = this.getTripManagementMethod();
            
            tripManagementMethod.changeTrip(trip);
            
            if (tripManagementMethod.hasError()) {
                return this.sendInternalServerErrorResponse(tripManagementMethod);
            }
            
            return this.sendOkResponse("Trip with ID " + entityId + " was successfully updated!");         
        } catch (EJBException e) {
            if (e.getCausedByException() instanceof NoResultException) {
                return this.sendNotFoundResponse(String.format(IMessages.NO_TRIP_WITH_ID, entityId));
            } else {
                throw e;
            }    
        }
    }

    @DELETE
    @Path("{" + PATH_PARAM_TRIP_ID + "}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@Context HttpHeaders headers, @PathParam(PATH_PARAM_TRIP_ID) Long entityId){
        Response response = super.delete(headers, entityId);
        
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
            TripManagementMethod tripManagementMethod = this.getTripManagementMethod();
            tripManagementMethod.cancelTrip(trip);
            
            if (tripManagementMethod.hasError()) {
                return this.sendInternalServerErrorResponse(tripManagementMethod);
            }
            
            return this.sendOkResponse("Cancelled trip with ID " + trip.getIdentity());
        } catch (EJBException e) {
            if (e.getCausedByException() instanceof NoResultException) {
                return this.sendNotFoundResponse(String.format(IMessages.NO_TRIP_WITH_ID, entityId));
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
