/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.util.math;

/**
 * <p>Simple model of coordinates.</p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 24.01.2015
 *
 */
public class Coordinates
{
    protected float latitude;
    protected float longitude;
    
    /**
     * 
     * @param latitudeDegrees
     * @param longitudeDegrees
     */
    public Coordinates(float latitudeDegrees, float longitudeDegrees)
    {
        this.setLatitudeDegrees(latitudeDegrees);
        this.setLongitude(longitudeDegrees); 
    }
    
    /**
     * @return the latitude
     */
    public float getLatitudeDegrees()
    {
        return latitude;
    }
    /**
     * @param latitude the latitude to set
     */
    public void setLatitudeDegrees(float latitude)
    {
        this.latitude = latitude;
    }
    
    /**
     * Get Longitude in radians.
     * @return
     */
    public double getLatitudeRadians()
    {
        return Math.toRadians(this.getLatitudeDegrees());
    }
    
    /**
     * @return the longitude
     */
    public float getLongitudeDegrees()
    {
        return longitude;
    }
    
    /**
     * Get Longitude in radians.
     * @return
     */
    public double getLongitudeRadians()
    {
        return Math.toRadians(this.getLongitudeDegrees());
    }
    
    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(float longitude)
    {
        this.longitude = longitude;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(latitude);
        result = prime * result + Float.floatToIntBits(longitude);
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
        Coordinates other = (Coordinates) obj;
        if (Float.floatToIntBits(latitude) != Float
                .floatToIntBits(other.latitude))
            return false;
        if (Float.floatToIntBits(longitude) != Float
                .floatToIntBits(other.longitude))
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
        builder.append("Coordinate [getLatitudeDegrees()=");
        builder.append(getLatitudeDegrees());
        builder.append(", getLongitudeDegrees()=");
        builder.append(getLongitudeDegrees());
        builder.append(", hashCode()=");
        builder.append(hashCode());
        builder.append("]");
        return builder.toString();
    }    
}
