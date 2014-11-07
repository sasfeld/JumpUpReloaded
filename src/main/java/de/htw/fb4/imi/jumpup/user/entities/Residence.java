/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.user.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Table;

import de.htw.fb4.imi.jumpup.entities.AbstractEntity;
import de.htw.fb4.imi.jumup.settings.ResidenceSettings;

/**
 * <p>Residence record.</p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 06.11.2014
 *
 */
@Entity
@Embeddable
@Table(name="residence")
public class Residence extends AbstractEntity
{
    @Column(name="town", nullable=true, updatable=true, length=ResidenceSettings.MAX_LENGTH_TOWN)
    protected String town;
    
    @Column(name="country", nullable=true, updatable=true, length=ResidenceSettings.MAX_LENGTH_COUNTRY)
    protected String country;
    
    @Column(name="locale", nullable=true, updatable=true, length=ResidenceSettings.MAXL_LENGTH_LOCALE)
    protected String locale;

    /**
     * @return the town
     */
    public final String getTown()
    {
        return town;
    }

    /**
     * @param town the town to set
     */
    public final void setTown(String town)
    {
        this.town = town;
    }

    /**
     * @return the country
     */
    public final String getCountry()
    {
        return country;
    }

    /**
     * @param country the country to set
     */
    public final void setCountry(String country)
    {
        this.country = country;
    }

    /**
     * @return the locale
     */
    public final String getLocale()
    {
        return locale;
    }

    /**
     * @param locale the locale to set
     */
    public final void setLocale(String locale)
    {
        this.locale = locale;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((country == null) ? 0 : country.hashCode());
        result = prime * result + ((locale == null) ? 0 : locale.hashCode());
        result = prime * result + ((town == null) ? 0 : town.hashCode());
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
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Residence other = (Residence) obj;
        if (country == null) {
            if (other.country != null)
                return false;
        } else if (!country.equals(other.country))
            return false;
        if (locale == null) {
            if (other.locale != null)
                return false;
        } else if (!locale.equals(other.locale))
            return false;
        if (town == null) {
            if (other.town != null)
                return false;
        } else if (!town.equals(other.town))
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
        builder.append("Residence [town=");
        builder.append(town);
        builder.append(", country=");
        builder.append(country);
        builder.append(", locale=");
        builder.append(locale);
        builder.append("]");
        return builder.toString();
    }

}

