/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.rest;

import javax.enterprise.context.Dependent;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * <p>Application's basic rest controller for general non-module-specific requests, such as OPTIONS.</p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 25.11.2015
 *
 */
@Path(BasicRestController.BASE_PATH)
@Dependent
public class BasicRestController extends AbstractRestController<AbstractRestModel>
{   
    public static final String BASE_PATH = "/";
    public static final String SCHEME = "http";
    
    private static final String CURRENT_API_VERSION = "1";

    @OPTIONS
    @Override
    public Response options(@Context HttpHeaders headers)
    {
        return Response.ok(
                buildDocumentation()                
                )
                .type(MediaType.TEXT_PLAIN)
                .build();
    }

    private String buildDocumentation()
    {
        return "JumpUp.ME - API\n\nCurrent API version: " + CURRENT_API_VERSION;
    }
}
