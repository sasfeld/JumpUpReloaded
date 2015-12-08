/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.user.rest.v1;

import java.io.Serializable;

import javax.ws.rs.Path;

import de.htw.fb4.imi.jumpup.user.rest.SecuredBaseController;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 07.12.2015
 *
 */
@Path(V1SecuredController.VERSION_PATH + SecuredBaseController.PATH)
public class V1SecuredController extends SecuredBaseController implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 8826331179494928634L;
    
    public static final String VERSION_PATH = "/v1.0.0";    


    @Override
    protected boolean isEnabled()
    {
        return true;
    }
}
