/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.rest;

import javax.inject.Named;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.htw.fb4.imi.jumpup.settings.BeanNames;

/**
 * <p>Application's basic rest controller for general non-module-specific requests, such as OPTIONS.</p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 25.11.2015
 *
 */
@Named(value = BeanNames.BASIC_REST_CONTROLLER)
@Path("/")
public class BasicRestController extends AbstractRestController
{   
    private static final String CURRENT_API_VERSION = "1";

    @OPTIONS
    @Override
    public Response options()
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
