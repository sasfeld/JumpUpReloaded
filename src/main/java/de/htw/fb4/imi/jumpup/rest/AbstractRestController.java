/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.rest;

import java.net.URI;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import de.htw.fb4.imi.jumpup.rest.methods.IDelete;
import de.htw.fb4.imi.jumpup.rest.methods.IGet;
import de.htw.fb4.imi.jumpup.rest.methods.IOptions;
import de.htw.fb4.imi.jumpup.rest.methods.IPost;
import de.htw.fb4.imi.jumpup.rest.methods.IPut;
import de.htw.fb4.imi.jumpup.util.ErrorPrintable;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 25.11.2015
 *
 */
public abstract class AbstractRestController<T> implements IGet, IPost<T>, IPut<T>, IDelete, IOptions
{
    protected boolean isEnabled() {
        return false;
    }
    
    @GET
    public Response get(@Context HttpHeaders headers) {
        if (!this.isEnabled()) {
            return this.sendVersionDisabledResponse();
        }
        
        return null;
    }   
    
    @GET
    public Response get(@Context HttpHeaders headers, Long... ids) {
        if (!this.isEnabled()) {
            return this.sendVersionDisabledResponse();
        }
        
        return null;
    }

    @POST
    public Response post(@Context HttpHeaders headers, T abstractRestModel){
        if (!this.isEnabled()) {
            return this.sendVersionDisabledResponse();
        }
        
        return null;
    }   
    
    @PUT
    public Response put(@Context HttpHeaders headers, Long entityId, T abstractRestModel){
        if (!this.isEnabled()) {
            return this.sendVersionDisabledResponse();
        }
        
        return null;
    }   
    
    @DELETE
    public Response delete(@Context HttpHeaders headers, Long entityId){
        if (!this.isEnabled()) {
            return this.sendVersionDisabledResponse();
        }
        
        return null;
    }   
    
    @OPTIONS
    public Response options(@Context HttpHeaders headers){
        if (!this.isEnabled()) {
            return this.sendVersionDisabledResponse();
        }
        
        return null;
    }   
    
    private Response sendVersionDisabledResponse()
    {
        return Response
                .notAcceptable(null)
                .build();
    }
    
    protected Response sendNotFoundResponse()
    {
        return Response
                .status(Status.NOT_FOUND)
                .build();
    }    

    protected Response sendInternalServerErrorResponse(
            ErrorPrintable errorPrintable)
    {
        return Response
                .status(Status.INTERNAL_SERVER_ERROR)
                .build();
    }     

    protected Response sendOkResponse(String message)
    {
        return Response
                .ok(message)
                .build();
    }
    
    protected Response sendCreatedResponse(URI locationUri)
    {
        return Response
                .created(locationUri)
                .build();
    }
}
