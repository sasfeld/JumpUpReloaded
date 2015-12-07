/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.rest;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 07.12.2015
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class ErrorResponse
{
    protected List<String> errors = new ArrayList<String>();
    protected Boolean success = false;
    
    public void setErrors(List<String> errors)
    {
        this.errors = errors;
    }
    
    public List<String> getErrors()
    {
        return errors;
    }
    
    public void setSuccess(boolean success)
    {
        this.success = success;
    }

    public Boolean getSuccess()
    {
        return success;
    }
    
    public void addError(String errorMessage)
    {
       this.errors.add(errorMessage);        
    }

    public void setErrors(String[] errors2)
    {
       for (String error : errors2) {
         addError(error);
       }        
    }
}
