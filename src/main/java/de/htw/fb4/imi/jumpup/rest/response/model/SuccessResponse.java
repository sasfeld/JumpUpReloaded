/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.rest.response.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 14.12.2015
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class SuccessResponse extends BasicResponse
{
    protected List<String> messages = new ArrayList<String>();
    
    public SuccessResponse()
    {
      super();
      
      this.setSuccess(true);
    }
    
    public void setMessages(List<String> messages)
    {
        this.messages = messages;
    }
    
    public List<String> getMessages()
    {
        return messages;
    }
   
    
    public void addMessage(String message)
    {
       this.messages.add(message);        
    }

    public void setMessages(String[] messages)
    {
       for (String message : messages) {
           addMessage(message);
       }        
    }

}
