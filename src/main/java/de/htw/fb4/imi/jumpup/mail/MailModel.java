/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.mail;

import java.util.HashSet;
import java.util.Set;

import javax.mail.internet.InternetAddress;

/**
 * <p>
 * This mailmodel is given to the {@link MailAdapter} implementation.
 * </p>
 * 
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 28.11.2014
 * 
 */
public class MailModel
{
    protected InternetAddress sender;
    protected Set<InternetAddress> recipients;
    protected String subject;
    protected String contentText;
    protected String contentHtml;

   

    public MailModel()
    {
        this.initialize();
    }

    protected void initialize()
    {
        this.recipients = new HashSet<>();
    }

    /**
     * <p>
     * Set the sender (FROM part).
     * </p>
     * 
     * <p>
     * This can be a single eMail-address only or an eMail-address and name,
     * such as: <br />
     * 
     * Max Mustermann <max@mustermann.de>
     * </p>
     * 
     * @param sender
     * @throws NullPointerException
     */
    public void setSender(final InternetAddress sender)
    {
        if (null == sender) {
            throw new NullPointerException("Null is not allowed as parameter value.");
        }
        
        this.sender = sender;
    }
    
    /**
     * 
     * @return
     */
    public InternetAddress getSender()
    {
        return this.sender;
    }

    /**
     * <p>
     * Add a recipient.
     * </p>
     * 
     * <p>
     * This can be a single eMail-address only.
     * </p>
     * 
     * @param recipient
     * @throws NullPointerException
     */
    public void addRecipient(final InternetAddress recipient)
    {
        if (null == recipient) {
            throw new NullPointerException("Null is not allowed as value for recipient.");
        }        
        
        this.recipients.add(recipient);
    }
    
    /**
     * 
     * @return
     */
    public InternetAddress[] getRecipients()
    {
        return this.recipients.toArray(new InternetAddress[recipients.size()]);
    }

    /**
     * @return the subject
     */
    public String getSubject()
    {
        return subject;
    }

    /**
     * @param subject the subject to set
     */
    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    /**
     * @return the contentText
     */
    public String getContentText()
    {
        return contentText;
    }

    /**
     * @param contentText the contentText to set
     */
    public void setContentText(String contentText)
    {
        this.contentText = contentText;
    }

    /**
     * @return the contentHtml
     */
    public String getContentHtml()
    {
        return contentHtml;
    }

    /**
     * @param contentHtml the contentHtml to set
     */
    public void setContentHtml(String contentHtml)
    {
        this.contentHtml = contentHtml;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((contentHtml == null) ? 0 : contentHtml.hashCode());
        result = prime * result
                + ((contentText == null) ? 0 : contentText.hashCode());
        result = prime * result
                + ((recipients == null) ? 0 : recipients.hashCode());
        result = prime * result + ((sender == null) ? 0 : sender.hashCode());
        result = prime * result + ((subject == null) ? 0 : subject.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MailModel other = (MailModel) obj;
        if (contentHtml == null) {
            if (other.contentHtml != null)
                return false;
        } else if (!contentHtml.equals(other.contentHtml))
            return false;
        if (contentText == null) {
            if (other.contentText != null)
                return false;
        } else if (!contentText.equals(other.contentText))
            return false;
        if (recipients == null) {
            if (other.recipients != null)
                return false;
        } else if (!recipients.equals(other.recipients))
            return false;
        if (sender == null) {
            if (other.sender != null)
                return false;
        } else if (!sender.equals(other.sender))
            return false;
        if (subject == null) {
            if (other.subject != null)
                return false;
        } else if (!subject.equals(other.subject))
            return false;
        return true;
    } 

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("MailModel [sender=");
        builder.append(sender);
        builder.append(", recipients=");
        
        for (InternetAddress recipient : recipients) {
            builder.append(recipient + ",");
        }
        
        builder.append(", subject=");
        builder.append(subject);
        builder.append(", contentText=");
        builder.append(contentText);
        builder.append(", contentHtml=");
        builder.append(contentHtml);
        builder.append("]");
        return builder.toString();
    }
}
