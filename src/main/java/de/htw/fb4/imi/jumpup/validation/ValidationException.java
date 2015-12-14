/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.validation;

import java.util.Set;

import de.htw.fb4.imi.jumpup.validation.validator.JumpUpValidator;

/**
 * <p>A validation exception is thrown if a {@link JumpUpValidator} fails, e.g. within a webservice.</p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 14.12.2015
 *
 */
public class ValidationException extends Exception
{
    /**
     * 
     */
    private static final long serialVersionUID = 3365750235029366451L;
    
    private String fieldName;
    private Set<String> validationErrorMessages;

    public ValidationException(String fieldname, Set<String> validationErrorMessages)
    {
        super();
        
        this.fieldName = fieldname;
        this.validationErrorMessages = validationErrorMessages;
    }

    public String getMessage()
    {
        StringBuilder builder = new StringBuilder();
        
        builder.append("The field " + fieldName + " is not valid.\n");
        
        for (String validationErrorMessage : validationErrorMessages) {
            builder.append(validationErrorMessage + "\n");
        }
        
        return builder.toString();
    }   
    
    public String getFieldName()
    {
        return fieldName;
    }
    
    public Set<String> getValidationErrorMessages()
    {
        return validationErrorMessages;
    }
}
