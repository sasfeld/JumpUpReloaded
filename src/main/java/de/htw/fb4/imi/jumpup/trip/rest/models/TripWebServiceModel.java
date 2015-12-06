/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.trip.rest.models;

import java.sql.Timestamp;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import de.htw.fb4.imi.jumpup.rest.AbstractRestModel;
import de.htw.fb4.imi.jumpup.user.entities.User;
import de.htw.fb4.imi.jumpup.verhicle.entities.Vehicle;

/**
 * <p>Plain old object that will be (un-)marshalled and sent to REST callers.</p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 02.12.2015
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class TripWebServiceModel extends AbstractRestModel
{
    protected long identity;
    protected Timestamp creationTimestamp;
    protected Timestamp updateTimestamp;

    protected String startpoint;
    protected String endpoint;
    protected double latStartpoint;
    protected double longStartpoint;
    protected double latEndpoint;
    protected double longEndpoint;
    protected Timestamp startDateTime;
    protected Timestamp endDateTime;
    protected float price;
    protected String overViewPath;
    protected String viaWaypoints;
    protected Integer numberOfSeats;
    protected Vehicle vehicle;
    protected User driver;
    protected Timestamp cancelationDateTime;
    protected long distanceMeters;
    protected long durationSeconds;
    
    public void setIdentity(long identity)
    {
        this.identity = identity;
    }
    
    public long getIdentity()
    {
        return identity;
    }
    
    public void setCreationTimestamp(Timestamp creationTimestamp)
    {
        this.creationTimestamp = creationTimestamp;
    }
    
    public Timestamp getCreationTimestamp()
    {
        return creationTimestamp;
    }
    
    public void setUpdateTimestamp(Timestamp updateTimestamp)
    {
        this.updateTimestamp = updateTimestamp;
    }
    
    public Timestamp getUpdateTimestamp()
    {
        return updateTimestamp;
    }
    
    /**
     * @return the startpoint
     */
    public String getStartpoint()
    {
        return startpoint;
    }

    /**
     * @param startpoint
     *            the startpoint to set
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
     * @param endpoint
     *            the endpoint to set
     */
    public void setEndpoint(String endpoint)
    {
        this.endpoint = endpoint;
    }

    /**
     * @return the latStartpoint
     */
    public double getLatStartpoint()
    {
        return latStartpoint;
    }

    /**
     * @param latStartpoint
     *            the latStartpoint to set
     */
    public void setLatStartpoint(double latStartpoint)
    {
        this.latStartpoint = latStartpoint;
    }

    /**
     * @return the longStartpoint
     */
    public double getLongStartpoint()
    {
        return longStartpoint;
    }

    /**
     * @param longStartpoint
     *            the longStartpoint to set
     */
    public void setLongStartpoint(double longStartpoint)
    {
        this.longStartpoint = longStartpoint;
    }

    /**
     * @return the latEntpoint
     */
    public double getLatEndpoint()
    {
        return latEndpoint;
    }

    /**
     * @param latEndpoint
     *            the latEntpoint to set
     */
    public void setLatEndpoint(double latEndpoint)
    {
        this.latEndpoint = latEndpoint;
    }

    /**
     * @return the longEndpoint
     */
    public double getLongEndpoint()
    {
        return longEndpoint;
    }

    /**
     * @param longEndpoint
     *            the longEndpoint to set
     */
    public void setLongEndpoint(double longEndpoint)
    {
        this.longEndpoint = longEndpoint;
    }

    /**
     * @return the startDateTime
     */
    public Date getStartDateTime()
    {
        if (null == this.startDateTime) {
            return null;
        }

        return new Date(this.startDateTime.getTime());
    }

    /**
     * @param startDateTime
     *            the startDateTime to set
     */
    public void setStartDateTime(Timestamp startDateTime)
    {
        this.startDateTime = startDateTime;
    }

    /**
     * 
     * @param startDateTime
     */
    public void setStartDateTime(Date startDateTime)
    {
        this.setStartDateTime(new Timestamp(startDateTime.getTime()));
    }

    /**
     * @return the endDateTime
     */
    public Date getEndDateTime()
    {
        if (null == this.endDateTime) {
            return null;
        }

        return new Date(this.endDateTime.getTime());
    }

    /**
     * @param endDateTime
     *            the endDateTime to set
     */
    public void setEndDateTime(Timestamp endDateTime)
    {
        this.endDateTime = endDateTime;
    }

    /**
     * 
     * @param endDateTime
     */
    public void setEndDateTime(Date endDateTime)
    {
        this.setEndDateTime(new Timestamp(endDateTime.getTime()));
    }

    /**
     * @return the price
     */
    public float getPrice()
    {
        return price;
    }

    /**
     * @param price
     *            the price to set
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
     * @param overViewPath
     *            the overViewPath to set
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
     * @param viaWaypoints
     *            the viaWaypoints to set
     */
    public void setViaWaypoints(String viaWaypoints)
    {
        this.viaWaypoints = viaWaypoints;
    }

    /**
     * @return the numberOfSeats
     */
    public Integer getNumberOfSeats()
    {
        return numberOfSeats;
    }

    /**
     * @param numberOfSeats
     *            the numberOfSeats to set
     */
    public void setNumberOfSeats(Integer numberOfSeats)
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
     * @param vehicle
     *            the vehicle to set
     */
    public void setVehicle(Vehicle vehicle)
    {
        this.vehicle = vehicle;
    }

    /**
     * @return the driver
     */
    public User getDriver()
    {
        return driver;
    }

    /**
     * @param driver
     *            the driver to set
     */
    public void setDriver(User driver)
    {
        this.driver = driver;
    }

    /**
     * @return the cancelationDateTime
     */
    public Timestamp getCancelationDateTime()
    {
        return cancelationDateTime;
    }

    /**
     * @param cancelationDateTime
     *            the cancelationDateTime to set
     */
    public void setCancelationDateTime(Timestamp cancelationDateTime)
    {
        this.cancelationDateTime = cancelationDateTime;
    }
    
    /**
     * @return the distanceMeters
     */
    public long getDistanceMeters()
    {
        return distanceMeters;
    }

    /**
     * @param distanceMeters
     *            the distanceMeters to set
     */
    public void setDistanceMeters(long distanceMeters)
    {
        this.distanceMeters = distanceMeters;
    }

    /**
     * @return the durationSeconds
     */
    public long getDurationSeconds()
    {
        return durationSeconds;
    }

    /**
     * @param durationSeconds
     *            the durationSeconds to set
     */
    public void setDurationSeconds(long durationSeconds)
    {
        this.durationSeconds = durationSeconds;
    }
}
