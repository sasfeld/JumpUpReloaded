/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

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

    @Column(name = "created_at", nullable = false, insertable = true, updatable = false)
    protected Timestamp creationTimestamp;
    
    @Column(name = "updated_at", nullable = true, insertable = false, updatable = true)
    protected Timestamp updateTimestamp;
    
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
    public Timestamp getCreationTimestamp()
    {
        return creationTimestamp;
    }

    /**
     * @param creationTimestamp the creationTimestamp to set
     */
    public void setCreationTimestamp(Timestamp creationTimestamp)
    {
        this.creationTimestamp = creationTimestamp;
    }

    /**
     * @return the updateTimestamp
     */
    public Timestamp getUpdateTimestamp()
    {
        return updateTimestamp;
    }

    /**
     * @param updateTimestamp the updateTimestamp to set
     */
    public void setUpdateTimestamp(Timestamp updateTimestamp)
    {
        this.updateTimestamp = updateTimestamp;
    }

    @PrePersist
    /**
     * Set creation timestamp on creation of entity.
     */
    public void onCreate() 
    {
        if (null == this.creationTimestamp) {
            Timestamp timeStamp = this.getCurrentTimestamp();
            
            this.setCreationTimestamp(timeStamp);
        }
    }
    
    @PreUpdate
    /**
     * Set update timestamp on update of the entity.
     */
    public void onUpdate()
    {
        Timestamp timeStamp = this.getCurrentTimestamp();
        
        this.setUpdateTimestamp(timeStamp);
    }

    /**
     * 
     * @return
     */
    private Timestamp getCurrentTimestamp()
    {
        return new Timestamp(new Date().getTime());
    }
}
