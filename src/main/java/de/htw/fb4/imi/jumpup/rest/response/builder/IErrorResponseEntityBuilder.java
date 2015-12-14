/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.rest.response.builder;

import de.htw.fb4.imi.jumpup.rest.controller.BasicRestController;
import de.htw.fb4.imi.jumpup.rest.response.model.ErrorResponse;
import de.htw.fb4.imi.jumpup.rest.response.model.ValidationErrorResponse;
import de.htw.fb4.imi.jumpup.validation.ValidationException;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 07.12.2015
 *
 */
public interface IErrorResponseEntityBuilder
{
    
    String MESSAGE_VERSION_DISABLED = "API Version is disabled. Please have a look at the current API documentation or run OPTIONS on " + BasicRestController.BASE_PATH + " to find out the current version.";
    String MESSAGE_FORBIDDEN = "The currently authenticated user isn't authorized to access / modify the current resouce.";

    ErrorResponse buildMessageFromErrorString(String errorMessage);
    ErrorResponse buildMessageFromErrorString(String errorMessage, boolean success);    
    ErrorResponse buildMessageFromErrorArray(String[] errors);
    ErrorResponse buildMessageFromErrorArray(String[] errors, boolean success);
    
    ValidationErrorResponse buildMessageFromValidationException(ValidationException e);

}
