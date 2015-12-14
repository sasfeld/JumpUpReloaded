/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup;

/**
 * <p>Exception class for user-presentable messages.</p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 14.12.2015
 *
 */
public class ApplicationUserException extends Exception
{
    /**
     * 
     */
    private static final long serialVersionUID = 693725652191282914L;
    private String userMsg;

    public ApplicationUserException(String logMsg, String userMsg)
    {
        super(logMsg);
        
        this.userMsg = userMsg;
    }
    
    public String getUserMsg()
    {
        return userMsg;
    }
}
