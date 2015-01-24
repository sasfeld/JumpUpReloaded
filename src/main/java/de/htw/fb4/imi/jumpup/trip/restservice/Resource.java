/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.trip.restservice;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import de.htw.fb4.imi.jumpup.Application;
import de.htw.fb4.imi.jumpup.Application.LogType;

/**
 * <p>
 * Base class for Rest-Service management via Jax-RS.
 * </p>
 * 
 * @author <a href="mailto:m_seidler@hotmail.de">Marco Seidler</a>
 * @since 24.01.2015
 * 
 */
@Path("/lookuptrips")
public class Resource
{

    /**
     * 
     * @param tripSearch
     * @return
     */
    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Status searchForTrip(final TripSearchEntity tripSearch)
    {
        Application.log("Resource: TripSearchEntity= " + tripSearch,
                LogType.DEBUG, getClass());

        return Status.OK;
    }

}
