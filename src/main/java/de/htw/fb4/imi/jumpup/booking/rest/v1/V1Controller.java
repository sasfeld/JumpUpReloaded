/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.booking.rest.v1;

import javax.ws.rs.Path;

import de.htw.fb4.imi.jumpup.booking.rest.BaseController;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 02.01.2016
 *
 */
@Path(V1Controller.VERSION_PATH + BaseController.PATH)
public class V1Controller extends BaseController
{
    public static final String VERSION_PATH = "/v1.0.0";   
    
    @Override
    protected boolean isEnabled()
    {
        return true;
    }
}
