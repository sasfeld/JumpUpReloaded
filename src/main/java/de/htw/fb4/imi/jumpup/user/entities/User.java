/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.user.entities;

import java.util.Arrays;

import javax.inject.Inject;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

import de.htw.fb4.imi.jumpup.entities.AbstractEntity;
import de.htw.fb4.imi.jumpup.settings.UserSettings;
import de.htw.fb4.imi.jumpup.user.util.HashGenerable;

/**
 * <p>
 * The basic user entity.
 * </p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 06.11.2014
 *
 */
@Entity
@Table(name = "user")
public class User extends AbstractEntity {
    @Column(name = "username", nullable = false, updatable = true, unique = true, length = UserSettings.MAX_LENGTH_USERNAME)
    protected String username;

    @Column(name = "email", nullable = false, updatable = true, unique = true, length = UserSettings.MAX_LENGTH_MAIL)
    protected String eMail;

    @Column(name = "prename", nullable = false, updatable = false)
    protected String prename;

    @Column(name = "lastname", nullable = false, updatable = false)
    protected String lastname;

    @Column(name = "passwordhash", nullable = false, updatable = true, columnDefinition = "BINARY(32)")
    protected byte[] passwordHash;

    @Embedded
    protected Residence residence;

    @Column(name = "is_confirmed", nullable = false, updatable = true)
    protected Boolean isConfirmed;

    /**
     * Hash generable.
     * 
     * TODO add hash generable by dependency injection.
     */
    @Inject
    protected HashGenerable hashGenerable;

    /**
     * @return the username
     */
    public final String getUsername() {
        return username;
    }

    /**
     * @param username
     *            the username to set
     */
    public final void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the eMail
     */
    public final String geteMail() {
        return eMail;
    }

    /**
     * @param eMail
     *            the eMail to set
     */
    public final void setEmail(String eMail) {
        this.eMail = eMail;
    }

    /**
     * @return the prename
     */
    public final String getPrename() {
        return prename;
    }

    /**
     * @param prename
     *            the prename to set
     */
    public final void setPrename(String prename) {
        this.prename = prename;
    }

    /**
     * @return the lastname
     */
    public final String getLastname() {
        return lastname;
    }

    /**
     * @param lastname
     *            the lastname to set
     */
    public final void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * @return the passwordHash
     */
    public final byte[] getPasswordHash() {
        return passwordHash;
    }

    /**
     * Set the raw password.
     * 
     * This will be hashed and save on this entity.
     * 
     * @param password
     */
    public final void setPassword(String password) {
        if (null == this.hashGenerable) {
            throw new AssertionError(
                    "No hashGenerable instance given - can't generate password hash. Please make sure that the dependency injection is configured correctly.");
        }

        this.setPasswordHash(this.hashGenerable.generateHash(password));
    }

    /**
     * @param passwordHash
     *            the passwordHash to set
     */
    protected final void setPasswordHash(byte[] passwordHash) {
        this.passwordHash = passwordHash;
    }

    /**
     * @return the residence
     */
    public final Residence getResidence() {
        return residence;
    }

    /**
     * @param residence
     *            the residence to set
     */
    public final void setResidence(Residence residence) {
        this.residence = residence;
    }

    /**
     * @return the isConfirmed
     */
    public final Boolean getIsConfirmed() {
        return isConfirmed;
    }

    /**
     * @param isConfirmed
     *            the isConfirmed to set
     */
    public final void setIsConfirmed(Boolean isConfirmed) {
        this.isConfirmed = isConfirmed;
    }

    /**
     * Set the {@link HashGenerable} instance which will be used to digest the
     * password.
     * 
     * @param newPasswordHashGenerable
     */
    public void setHashGenerable(final HashGenerable newPasswordHashGenerable) {
        this.hashGenerable = newPasswordHashGenerable;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((eMail == null) ? 0 : eMail.hashCode());
        result = prime * result
                + ((isConfirmed == null) ? 0 : isConfirmed.hashCode());
        result = prime * result
                + ((lastname == null) ? 0 : lastname.hashCode());
        result = prime * result + Arrays.hashCode(passwordHash);
        result = prime * result + ((prename == null) ? 0 : prename.hashCode());
        result = prime * result
                + ((residence == null) ? 0 : residence.hashCode());
        result = prime * result
                + ((username == null) ? 0 : username.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
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
        if (!Arrays.equals(passwordHash, other.passwordHash))
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

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
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
        builder.append(Arrays.toString(passwordHash));
        builder.append(", residence=");
        builder.append(residence);
        builder.append(", isConfirmed=");
        builder.append(isConfirmed);
        builder.append("]");
        return builder.toString();
    }

}
