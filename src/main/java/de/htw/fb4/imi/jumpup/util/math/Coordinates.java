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
    protected double latitude;
    protected double longitude;
    
    /**
     * 
     * @param latitudeDegrees
     * @param longitudeDegrees
     */
    public Coordinates(double latitudeDegrees, double longitudeDegrees)
    {
        this.setLatitudeDegrees(latitudeDegrees);
        this.setLongitude(longitudeDegrees); 
    }
    
    /**
     * @return the latitude
     */
    public double getLatitudeDegrees()
    {
        return latitude;
    }
    /**
     * @param latitude the latitude to set
     */
    public void setLatitudeDegrees(double latitude)
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
    public double getLongitudeDegrees()
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
    public void setLongitude(double longitude)
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
        long temp;
        temp = Double.doubleToLongBits(latitude);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = prime * result + (int) (temp ^ (temp >>> 32));
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
        if (Double.doubleToLongBits(latitude) != Double
                .doubleToLongBits(other.latitude))
            return false;
        if (Double.doubleToLongBits(longitude) != Double
                .doubleToLongBits(other.longitude))
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
