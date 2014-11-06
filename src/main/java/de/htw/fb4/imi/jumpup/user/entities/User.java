/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.user.entities;

import javax.persistence.Entity;

/**
 * <p>The basic user entity.</p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 06.11.2014
 *
 */
@Entity
public class User
{
    protected String username;
    
    protected String eMail;
    
    protected String prename;
    
    protected String lastname;
    
    protected String passwordHash;
    
    protected Residence residence;
    
    protected Boolean isConfirmed;

    /**
     * @return the username
     */
    public final String getUsername()
    {
        return username;
    }

    /**
     * @param username the username to set
     */
    public final void setUsername(String username)
    {
        this.username = username;
    }

    /**
     * @return the eMail
     */
    public final String geteMail()
    {
        return eMail;
    }

    /**
     * @param eMail the eMail to set
     */
    public final void seteMail(String eMail)
    {
        this.eMail = eMail;
    }

    /**
     * @return the prename
     */
    public final String getPrename()
    {
        return prename;
    }

    /**
     * @param prename the prename to set
     */
    public final void setPrename(String prename)
    {
        this.prename = prename;
    }

    /**
     * @return the lastname
     */
    public final String getLastname()
    {
        return lastname;
    }

    /**
     * @param lastname the lastname to set
     */
    public final void setLastname(String lastname)
    {
        this.lastname = lastname;
    }

    /**
     * @return the passwordHash
     */
    public final String getPasswordHash()
    {
        return passwordHash;
    }

    /**
     * @param passwordHash the passwordHash to set
     */
    public final void setPasswordHash(String passwordHash)
    {
        this.passwordHash = passwordHash;
    }

    /**
     * @return the residence
     */
    public final Residence getResidence()
    {
        return residence;
    }

    /**
     * @param residence the residence to set
     */
    public final void setResidence(Residence residence)
    {
        this.residence = residence;
    }

    /**
     * @return the isConfirmed
     */
    public final Boolean getIsConfirmed()
    {
        return isConfirmed;
    }

    /**
     * @param isConfirmed the isConfirmed to set
     */
    public final void setIsConfirmed(Boolean isConfirmed)
    {
        this.isConfirmed = isConfirmed;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((eMail == null) ? 0 : eMail.hashCode());
        result = prime * result
                + ((isConfirmed == null) ? 0 : isConfirmed.hashCode());
        result = prime * result
                + ((lastname == null) ? 0 : lastname.hashCode());
        result = prime * result
                + ((passwordHash == null) ? 0 : passwordHash.hashCode());
        result = prime * result + ((prename == null) ? 0 : prename.hashCode());
        result = prime * result
                + ((residence == null) ? 0 : residence.hashCode());
        result = prime * result
                + ((username == null) ? 0 : username.hashCode());
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
        User other = (User) obj;
        if (eMail == null) {
            if (other.eMail != null)
                return false;
        } else if (!eMail.equals(other.eMail))
            return false;
        if (isConfirmed == null) {
            if (other.isConfirmed != null)
                return false;
        } else if (!isConfirmed.equals(other.isConfirmed))
            return false;
        if (lastname == null) {
            if (other.lastname != null)
                return false;
        } else if (!lastname.equals(other.lastname))
            return false;
        if (passwordHash == null) {
            if (other.passwordHash != null)
                return false;
        } else if (!passwordHash.equals(other.passwordHash))
            return false;
        if (prename == null) {
            if (other.prename != null)
                return false;
        } else if (!prename.equals(other.prename))
            return false;
        if (residence == null) {
            if (other.residence != null)
                return false;
        } else if (!residence.equals(other.residence))
            return false;
        if (username == null) {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
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
        builder.append("User [username=");
        builder.append(username);
        builder.append(", eMail=");
        builder.append(eMail);
        builder.append(", prename=");
        builder.append(prename);
        builder.append(", lastname=");
        builder.append(lastname);
        builder.append(", passwordHash=");
        builder.append(passwordHash);
        builder.append(", residence=");
        builder.append(residence);
        builder.append(", isConfirmed=");
        builder.append(isConfirmed);
        builder.append("]");
        return builder.toString();
    }
}

