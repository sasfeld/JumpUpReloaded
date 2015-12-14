/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.rest.response.builder;

import javax.ejb.Stateless;
import javax.inject.Inject;

import de.htw.fb4.imi.jumpup.rest.response.model.ErrorResponse;
import de.htw.fb4.imi.jumpup.rest.response.model.SuccessResponse;
import de.htw.fb4.imi.jumpup.settings.BeanNames;
import de.htw.fb4.imi.jumpup.translate.Translatable;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 07.12.2015
 *
 */
@Stateless(name=BeanNames.RESPONSE_BUILDER)
public class ResponseEntityBuilder implements IResponseEntityBuilder
{
    @Inject
    protected Translatable translator;
    
    public ErrorResponse newErrorResponse()
    {
        return new ErrorResponse();
    }

    @Override
    public ErrorResponse buildMessageFromErrorString(String errorMessage)
    {
        ErrorResponse res = newErrorResponse();
        res.addError(this.translator.translate(errorMessage));
        res.setSuccess(false);
        
        return res;
    }

    @Override
    public ErrorResponse buildMessageFromErrorArray(String[] errors)
    {
        for (int i = 0; i < errors.length; i++) {
            errors[i] = this.translator.translate(errors[i]);
        }
        
        ErrorResponse res = newErrorResponse();
        res.setErrors(errors);
        res.setSuccess(false);
        
        return res;
    }

    @Override
    public ErrorResponse buildMessageFromErrorString(String errorMessage,
            boolean success)
    {
        ErrorResponse response = this.buildMessageFromErrorString(errorMessage);
        response.setSuccess(success);
        
        return response;
    }

    @Override
    public ErrorResponse buildMessageFromErrorArray(String[] errors,
            boolean success)
    {
        ErrorResponse response = this.buildMessageFromErrorArray(errors);
        response.setSuccess(success);
        
        return response;
    }
    
    public SuccessResponse newSuccessResponse()
    {
        return new SuccessResponse();
    }

    @Override
    public SuccessResponse buildSuccessFromString(String successMessage)
    {
        SuccessResponse response = this.newSuccessResponse();
        response.addMessage(this.translator.translate(successMessage));
        
        return response;
    }

    @Override
    public SuccessResponse buildSuccessFromStringArray(String[] successMessages)
    {
        SuccessResponse response = this.newSuccessResponse();
        
        for (String successMessage : successMessages) {
            response.addMessage(this.translator.translate(successMessage));
        }      
        
        return response;
    }
}
