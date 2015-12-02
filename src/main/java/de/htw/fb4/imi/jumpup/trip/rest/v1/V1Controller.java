/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.trip.rest.v1;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.ws.rs.Path;

import de.htw.fb4.imi.jumpup.settings.BeanNames;
import de.htw.fb4.imi.jumpup.trip.rest.BaseController;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 25.11.2015
 *
 */
@Named(value = BeanNames.TRIP_REST_V1_CONTROLLER)
@Path(BaseController.PATH)
@SessionScoped
public class V1Controller extends BaseController implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1899194601570249626L;

    @Override
    protected boolean isEnabled()
    {
        return true;
    }
}
