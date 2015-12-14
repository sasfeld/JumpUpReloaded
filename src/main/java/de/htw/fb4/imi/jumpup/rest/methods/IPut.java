/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.rest.methods;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import de.htw.fb4.imi.jumpup.rest.response.model.AbstractRestModel;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 25.11.2015
 *
 */
public interface IPut<T extends AbstractRestModel>
{
    Response put(HttpHeaders headers, Long entityId, T abstractRestModel);
}
