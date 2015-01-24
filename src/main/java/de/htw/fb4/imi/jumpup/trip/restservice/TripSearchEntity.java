/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.trip.restservice;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * <p>
 * Entity Bean for the Rest-Service class {@link Resource}.
 * </p>
 * 
 * @author <a href="mailto:m_seidler@hotmail.de">Marco Seidler</a>
 * @since 24.01.2015
 * 
 */
@XmlRootElement
public class TripSearchEntity
{

    private String startLocation;

    private double latStartlocation;

    private double lonStartlocation;

    private String endLocation;

    private double latEndlocation;

    private double lonEndlocation;

    private Date startByDate;

    private Date endByDate;

    private float minPrice;

    private float maxPrice;

    private int maxDistance;

    public String getStartLocation()
    {
        return startLocation;
    }

    public void setStartLocation(String startLocation)
    {
        this.startLocation = startLocation;
    }

    public double getLatStartlocation()
    {
        return latStartlocation;
    }

    public void setLatStartlocation(double latStartlocation)
    {
        this.latStartlocation = latStartlocation;
    }

    public double getLonStartlocation()
    {
        return lonStartlocation;
    }

    public void setLonStartlocation(double lonStartlocation)
    {
        this.lonStartlocation = lonStartlocation;
    }

    public String getEndLocation()
    {
        return endLocation;
    }

    public void setEndLocation(String endLocation)
    {
        this.endLocation = endLocation;
    }

    public double getLatEndlocation()
    {
        return latEndlocation;
    }

    public void setLatEndlocation(double latEndlocation)
    {
        this.latEndlocation = latEndlocation;
    }

    public double getLonEndlocation()
    {
        return lonEndlocation;
    }

    public void setLonEndlocation(double lonEndlocation)
    {
        this.lonEndlocation = lonEndlocation;
    }

    public Date getStartByDate()
    {
        return startByDate;
    }

    public void setStartByDate(Date startByDate)
    {
        this.startByDate = startByDate;
    }

    public Date getEndByDate()
    {
        return endByDate;
    }

    public void setEndByDate(Date endByDate)
    {
        this.endByDate = endByDate;
    }

    public float getMinPrice()
    {
        return minPrice;
    }

    public void setMinPrice(float minPrice)
    {
        this.minPrice = minPrice;
    }

    public float getMaxPrice()
    {
        return maxPrice;
    }

    public void setMaxPrice(float maxPrice)
    {
        this.maxPrice = maxPrice;
    }

    public int getMaxDistance()
    {
        return maxDistance;
    }

    public void setMaxDistance(int maxDistance)
    {
        this.maxDistance = maxDistance;
    }

    @Override
    public String toString()
    {
        return "TripSearch [startLocation=" + startLocation
                + ", latStartlocation=" + latStartlocation
                + ", lonStartlocation=" + lonStartlocation + ", endLocation="
                + endLocation + ", latEndlocation=" + latEndlocation
                + ", lonEndlocation=" + lonEndlocation + ", startByDate="
                + startByDate + ", endByDate=" + endByDate + ", minPrice="
                + minPrice + ", maxPrice=" + maxPrice + ", maxDistance="
                + maxDistance + "]";
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((endByDate == null) ? 0 : endByDate.hashCode());
        result = prime * result
                + ((endLocation == null) ? 0 : endLocation.hashCode());
        long temp;
        temp = Double.doubleToLongBits(latEndlocation);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(latStartlocation);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(lonEndlocation);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(lonStartlocation);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + maxDistance;
        result = prime * result + Float.floatToIntBits(maxPrice);
        result = prime * result + Float.floatToIntBits(minPrice);
        result = prime * result
                + ((startByDate == null) ? 0 : startByDate.hashCode());
        result = prime * result
                + ((startLocation == null) ? 0 : startLocation.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TripSearchEntity other = (TripSearchEntity) obj;
        if (endByDate == null) {
            if (other.endByDate != null)
                return false;
        } else if (!endByDate.equals(other.endByDate))
            return false;
        if (endLocation == null) {
            if (other.endLocation != null)
                return false;
        } else if (!endLocation.equals(other.endLocation))
            return false;
        if (Double.doubleToLongBits(latEndlocation) != Double
                .doubleToLongBits(other.latEndlocation))
            return false;
        if (Double.doubleToLongBits(latStartlocation) != Double
                .doubleToLongBits(other.latStartlocation))
            return false;
        if (Double.doubleToLongBits(lonEndlocation) != Double
                .doubleToLongBits(other.lonEndlocation))
            return false;
        if (Double.doubleToLongBits(lonStartlocation) != Double
                .doubleToLongBits(other.lonStartlocation))
            return false;
        if (maxDistance != other.maxDistance)
            return false;
        if (Float.floatToIntBits(maxPrice) != Float
                .floatToIntBits(other.maxPrice))
            return false;
        if (Float.floatToIntBits(minPrice) != Float
                .floatToIntBits(other.minPrice))
            return false;
        if (startByDate == null) {
            if (other.startByDate != null)
                return false;
        } else if (!startByDate.equals(other.startByDate))
            return false;
        if (startLocation == null) {
            if (other.startLocation != null)
                return false;
        } else if (!startLocation.equals(other.startLocation))
            return false;
        return true;
    }

}
