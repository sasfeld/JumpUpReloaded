/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 06.11.2014
 *
 */
@MappedSuperclass
public class AbstractEntity implements Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -3703510461773241613L;

	@Id
    @GeneratedValue
    @Column(name = "identity")
    protected long identity;

    @Column(name = "created_at", nullable = false, updatable = false)
    protected long creationTimestamp;
    
    @Column(name = "updated_at", nullable = true, updatable = true)
    protected Long updateTimestamp;
    
    public AbstractEntity()
    {
        super();
    }

    /**
     * @return the identity
     */
    public long getIdentity()
    {
        return identity;
    }

    /**
     * @param identity the identity to set
     */
    public void setIdentity(long identity)
    {
        this.identity = identity;
    }

    /**
     * @return the creationTimestamp
     */
    public long getCreationTimestamp()
    {
        return creationTimestamp;
    }

    /**
     * @param creationTimestamp the creationTimestamp to set
     */
    public void setCreationTimestamp(long creationTimestamp)
    {
        this.creationTimestamp = creationTimestamp;
    }

    /**
     * @return the updateTimestamp
     */
    public Long getUpdateTimestamp()
    {
        return updateTimestamp;
    }

    /**
     * @param updateTimestamp the updateTimestamp to set
     */
    public void setUpdateTimestamp(Long updateTimestamp)
    {
        this.updateTimestamp = updateTimestamp;
    }

    
   
}
