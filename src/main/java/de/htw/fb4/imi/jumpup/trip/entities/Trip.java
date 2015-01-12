/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.trip.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import de.htw.fb4.imi.jumpup.entities.AbstractEntity;
import de.htw.fb4.imi.jumpup.verhicle.entities.Vehicle;

/**
 * <p>
 * Basis Entity for trips.
 * </p>
 * 
 * @author <a href="mailto:m_seidler@hotmail.de">Marco Seidler</a>
 * @since 03.01.2015
 * 
 */
@Entity
@Table(name = "trip")
@NamedQueries({
        @NamedQuery(name = Trip.NAME_QUERY_BY_STARTPOINT, query = "SELECT t FROM trip t WHERE t.startpoint = :startpoint"),
        @NamedQuery(name = Trip.NAME_QUERY_BY_ENDPOINT, query = "SELECT t FROM trip t WHERE t.endpoint = :endpoint") })
public class Trip extends AbstractEntity
{

    public static final String NAME_QUERY_BY_STARTPOINT = "NAME_QUERY_BY_STARTPOINT";
    public static final String NAME_QUERY_BY_ENDPOINT = "NAME_QUERY_BY_ENDPOINT";

    /**
     * 
     */
    private static final long serialVersionUID = -3854579506642418644L;

    @Column(name = "startpoint", nullable = false, updatable = true, unique = false)
    protected String startpoint;

    @Column(name = "endpoint", nullable = false, updatable = true, unique = false)
    protected String endpoint;

    @Column(name = "latstartpoint", nullable = false, updatable = true, unique = false)
    protected float latStartpoint;

    @Column(name = "longstartpoint", nullable = false, updatable = true, unique = false)
    protected float longStartpoint;

    @Column(name = "latendpoint", nullable = false, updatable = true, unique = false)
    protected float latEntpoint;

    @Column(name = "longendpoint", nullable = false, updatable = true, unique = false)
    protected float longEndpoint;

    @Column(name = "startdate", nullable = false, updatable = true, unique = false)
    protected  Date startDate;

    @Column(name = "enddate", nullable = false, updatable = true, unique = false)
    protected Date endDate;

    @Column(name = "price", nullable = false, updatable = true, unique = false)
    protected float price;

    @Column(name = "overviewpath", nullable = false, updatable = true, unique = false)
    protected String overViewPath;

    @Column(name = "viawaypoints", nullable = false, updatable = true, unique = false)
    protected String viaWaypoints;

    @Column(name = "numberofseats", nullable = false, updatable = true, unique = false)
    protected Byte numberOfSeats;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trips")
    protected Vehicle vehicle;

    /**
     * @return the startpoint
     */
    public String getStartpoint()
    {
        return startpoint;
    }

    /**
     * @param startpoint the startpoint to set
     */
    public void setStartpoint(String startpoint)
    {
        this.startpoint = startpoint;
    }

    /**
     * @return the endpoint
     */
    public String getEndpoint()
    {
        return endpoint;
    }

    /**
     * @param endpoint the endpoint to set
     */
    public void setEndpoint(String endpoint)
    {
        this.endpoint = endpoint;
    }

    /**
     * @return the latStartpoint
     */
    public float getLatStartpoint()
    {
        return latStartpoint;
    }

    /**
     * @param latStartpoint the latStartpoint to set
     */
    public void setLatStartpoint(float latStartpoint)
    {
        this.latStartpoint = latStartpoint;
    }

    /**
     * @return the longStartpoint
     */
    public float getLongStartpoint()
    {
        return longStartpoint;
    }

    /**
     * @param longStartpoint the longStartpoint to set
     */
    public void setLongStartpoint(float longStartpoint)
    {
        this.longStartpoint = longStartpoint;
    }

    /**
     * @return the latEntpoint
     */
    public float getLatEntpoint()
    {
        return latEntpoint;
    }

    /**
     * @param latEntpoint the latEntpoint to set
     */
    public void setLatEntpoint(float latEntpoint)
    {
        this.latEntpoint = latEntpoint;
    }

    /**
     * @return the longEndpoint
     */
    public float getLongEndpoint()
    {
        return longEndpoint;
    }

    /**
     * @param longEndpoint the longEndpoint to set
     */
    public void setLongEndpoint(float longEndpoint)
    {
        this.longEndpoint = longEndpoint;
    }

    /**
     * @return the startDate
     */
    public Date getStartDate()
    {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public Date getEndDate()
    {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }

    /**
     * @return the price
     */
    public float getPrice()
    {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(float price)
    {
        this.price = price;
    }

    /**
     * @return the overViewPath
     */
    public String getOverViewPath()
    {
        return overViewPath;
    }

    /**
     * @param overViewPath the overViewPath to set
     */
    public void setOverViewPath(String overViewPath)
    {
        this.overViewPath = overViewPath;
    }

    /**
     * @return the viaWaypoints
     */
    public String getViaWaypoints()
    {
        return viaWaypoints;
    }

    /**
     * @param viaWaypoints the viaWaypoints to set
     */
    public void setViaWaypoints(String viaWaypoints)
    {
        this.viaWaypoints = viaWaypoints;
    }

    /**
     * @return the numberOfSeats
     */
    public Byte getNumberOfSeats()
    {
        return numberOfSeats;
    }

    /**
     * @param numberOfSeats the numberOfSeats to set
     */
    public void setNumberOfSeats(Byte numberOfSeats)
    {
        this.numberOfSeats = numberOfSeats;
    }

    /**
     * @return the vehicle
     */
    public Vehicle getVehicle()
    {
        return vehicle;
    }

    /**
     * @param vehicle the vehicle to set
     */
    public void setVehicle(Vehicle vehicle)
    {
        this.vehicle = vehicle;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
        result = prime * result
                + ((endpoint == null) ? 0 : endpoint.hashCode());
        result = prime * result + Float.floatToIntBits(latEntpoint);
        result = prime * result + Float.floatToIntBits(latStartpoint);
        result = prime * result + Float.floatToIntBits(longEndpoint);
        result = prime * result + Float.floatToIntBits(longStartpoint);
        result = prime * result
                + ((numberOfSeats == null) ? 0 : numberOfSeats.hashCode());
        result = prime * result
                + ((overViewPath == null) ? 0 : overViewPath.hashCode());
        result = prime * result + Float.floatToIntBits(price);
        result = prime * result
                + ((startDate == null) ? 0 : startDate.hashCode());
        result = prime * result
                + ((startpoint == null) ? 0 : startpoint.hashCode());
        result = prime * result + ((vehicle == null) ? 0 : vehicle.hashCode());
        result = prime * result
                + ((viaWaypoints == null) ? 0 : viaWaypoints.hashCode());
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
        Trip other = (Trip) obj;
        if (endDate == null) {
            if (other.endDate != null)
                return false;
        } else if (!endDate.equals(other.endDate))
            return false;
        if (endpoint == null) {
            if (other.endpoint != null)
                return false;
        } else if (!endpoint.equals(other.endpoint))
            return false;
        if (Float.floatToIntBits(latEntpoint) != Float
                .floatToIntBits(other.latEntpoint))
            return false;
        if (Float.floatToIntBits(latStartpoint) != Float
                .floatToIntBits(other.latStartpoint))
            return false;
        if (Float.floatToIntBits(longEndpoint) != Float
                .floatToIntBits(other.longEndpoint))
            return false;
        if (Float.floatToIntBits(longStartpoint) != Float
                .floatToIntBits(other.longStartpoint))
            return false;
        if (numberOfSeats == null) {
            if (other.numberOfSeats != null)
                return false;
        } else if (!numberOfSeats.equals(other.numberOfSeats))
            return false;
        if (overViewPath == null) {
            if (other.overViewPath != null)
                return false;
        } else if (!overViewPath.equals(other.overViewPath))
            return false;
        if (Float.floatToIntBits(price) != Float.floatToIntBits(other.price))
            return false;
        if (startDate == null) {
            if (other.startDate != null)
                return false;
        } else if (!startDate.equals(other.startDate))
            return false;
        if (startpoint == null) {
            if (other.startpoint != null)
                return false;
        } else if (!startpoint.equals(other.startpoint))
            return false;
        if (vehicle == null) {
            if (other.vehicle != null)
                return false;
        } else if (!vehicle.equals(other.vehicle))
            return false;
        if (viaWaypoints == null) {
            if (other.viaWaypoints != null)
                return false;
        } else if (!viaWaypoints.equals(other.viaWaypoints))
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
        builder.append("Trip [getStartpoint()=");
        builder.append(getStartpoint());
        builder.append(", getEndpoint()=");
        builder.append(getEndpoint());
        builder.append(", getLatStartpoint()=");
        builder.append(getLatStartpoint());
        builder.append(", getLongStartpoint()=");
        builder.append(getLongStartpoint());
        builder.append(", getLatEntpoint()=");
        builder.append(getLatEntpoint());
        builder.append(", getLongEndpoint()=");
        builder.append(getLongEndpoint());
        builder.append(", getStartDate()=");
        builder.append(getStartDate());
        builder.append(", getEndDate()=");
        builder.append(getEndDate());
        builder.append(", getPrice()=");
        builder.append(getPrice());
        builder.append(", getOverViewPath()=");
        builder.append(getOverViewPath());
        builder.append(", getViaWaypoints()=");
        builder.append(getViaWaypoints());
        builder.append(", getNumberOfSeats()=");
        builder.append(getNumberOfSeats());
        builder.append(", getVehicle()=");
        builder.append(getVehicle());
        builder.append(", hashCode()=");
        builder.append(hashCode());
        builder.append("]");
        return builder.toString();
    }

    
}
