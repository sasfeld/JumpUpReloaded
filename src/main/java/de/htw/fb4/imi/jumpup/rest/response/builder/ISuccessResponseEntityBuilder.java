/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.rest.response.builder;

import de.htw.fb4.imi.jumpup.rest.response.model.SuccessResponse;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 07.12.2015
 *
 */
public interface ISuccessResponseEntityBuilder
{
    String MESSAGE_CREATED = "The %s with ID %d was successfully created.";
    String MESSAGE_UPDATED = "The %s with ID %d was successfully updated.";
    String MESSAGE_CANCELLED = "The %s with ID %d was successfully cancelled.";
    
    SuccessResponse buildSuccessFromString(String successMessage);  
    SuccessResponse buildSuccessFromStringArray(String[] successMessages);
}
