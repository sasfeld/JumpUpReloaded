/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.user.rest.v1;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.ws.rs.Path;

import de.htw.fb4.imi.jumpup.user.rest.UnsecuredBaseController;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 07.12.2015
 *
 */
@Path(V1UnsecuredController.VERSION_PATH + UnsecuredBaseController.PATH)
@Named
@RequestScoped
public class V1UnsecuredController extends UnsecuredBaseController implements Serializable
{    
    /**
     * 
     */
    private static final long serialVersionUID = 7931810171670232959L;
    public static final String VERSION_PATH = "/v1.0.0";    


    @Override
    protected boolean isEnabled()
    {
        return true;
    }
}
