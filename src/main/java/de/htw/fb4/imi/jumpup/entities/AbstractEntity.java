/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Column;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 06.11.2014
 *
 */
@Entity
public class AbstractEntity
{
    @Id
    @GeneratedValue
    @Column(name = "identity")
    protected long identity;

    @Column(name = "created_at", nullable = false, updatable = false)
    protected long creationTimestamp;
    
    @Column(name = "updated_at", nullable = true, updatable = true)
    protected long updateTimestamp;

    /**
     * @return the identity
     */
    public final long getIdentity()
    {
        return identity;
    }

    /**
     * @param identity the identity to set
     */
    public final void setIdentity(long identity)
    {
        this.identity = identity;
    }

    /**
     * @return the creationTimestamp
     */
    public final long getCreationTimestamp()
    {
        return creationTimestamp;
    }

    /**
     * @param creationTimestamp the creationTimestamp to set
     */
    public final void setCreationTimestamp(long creationTimestamp)
    {
        this.creationTimestamp = creationTimestamp;
    }

    /**
     * @return the updateTimestamp
     */
    public final long getUpdateTimestamp()
    {
        return updateTimestamp;
    }

    /**
     * @param updateTimestamp the updateTimestamp to set
     */
    public final void setUpdateTimestamp(long updateTimestamp)
    {
        this.updateTimestamp = updateTimestamp;
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
                + (int) (creationTimestamp ^ (creationTimestamp >>> 32));
        result = prime * result + (int) (identity ^ (identity >>> 32));
        result = prime * result
                + (int) (updateTimestamp ^ (updateTimestamp >>> 32));
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
        AbstractEntity other = (AbstractEntity) obj;
        if (creationTimestamp != other.creationTimestamp)
            return false;
        if (identity != other.identity)
            return false;
        if (updateTimestamp != other.updateTimestamp)
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
        builder.append("AbstractEntity [identity=");
        builder.append(identity);
        builder.append(", creationTimestamp=");
        builder.append(creationTimestamp);
        builder.append(", updateTimestamp=");
        builder.append(updateTimestamp);
        builder.append("]");
        return builder.toString();
    } 
   
}
