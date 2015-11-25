/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.trip.rest;

import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import de.htw.fb4.imi.jumpup.rest.SecuredRestController;

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

    @DELETE
    @Path("{" + PATH_PARAM_TRIP_ID + "}")
    public Response delete(@Context HttpHeaders headers, @PathParam(PATH_PARAM_TRIP_ID) Long entityId){
        Response response = super.get(headers);
        
        if (null != response) {
            return response;
        }
        
        return this.tryToDeleteTripAndReturnResponse(entityId);
    }

    private Response tryToDeleteTripAndReturnResponse(Long entityId)
    {
        return Response.ok(""
                + "Will delete trip with entity id " + entityId)
                .build();
    }   
}
