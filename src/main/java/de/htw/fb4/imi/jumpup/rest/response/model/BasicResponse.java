/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.rest.response.model;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 14.12.2015
 *
 */
public class BasicResponse
{
    protected Boolean success = false;    
    
    public void setSuccess(boolean success)
    {
        this.success = success;
    }

    public Boolean getSuccess()
    {
        return success;
    }
}
