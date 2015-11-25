/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.rest;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.core.Response;

import de.htw.fb4.imi.jumpup.rest.methods.IDelete;
import de.htw.fb4.imi.jumpup.rest.methods.IGet;
import de.htw.fb4.imi.jumpup.rest.methods.IOptions;
import de.htw.fb4.imi.jumpup.rest.methods.IPost;
import de.htw.fb4.imi.jumpup.rest.methods.IPut;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 25.11.2015
 *
 */
public abstract class AbstractRestController implements IGet, IPost, IPut, IDelete, IOptions
{
    protected boolean isEnabled() {
        return false;
    }
    
    @GET
    public Response get() {
        if (!this.isEnabled()) {
            return this.sendVersionDisabledResponse();
        }
        
        return null;
    }   

    @POST
    public Response post(){
        if (!this.isEnabled()) {
            return this.sendVersionDisabledResponse();
        }
        
        return null;
    }   
    
    @PUT
    public Response put(){
        if (!this.isEnabled()) {
            return this.sendVersionDisabledResponse();
        }
        
        return null;
    }   
    
    @DELETE
    public Response delete(){
        if (!this.isEnabled()) {
            return this.sendVersionDisabledResponse();
        }
        
        return null;
    }   
    
    @OPTIONS
    public Response options(){
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
}
