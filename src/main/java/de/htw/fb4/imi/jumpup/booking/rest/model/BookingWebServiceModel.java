/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.booking.rest.model;

import java.sql.Timestamp;

import de.htw.fb4.imi.jumpup.rest.response.model.AbstractRestModel;
import de.htw.fb4.imi.jumpup.user.Role;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 02.01.2016
 *
 */
public class BookingWebServiceModel extends AbstractRestModel
{
    private String startPoint;
    private String endPoint;
    private double startLatitude;
    private double startLongitude;
    private double endLatitude;
    private double endLongitude;
    private Timestamp confirmationDateTime;
    private Timestamp cancellationDateTime;
    private Role actorOnLastChange;
    private Long tripId;
    
    /**
     * @return the startPoint
     */
    public String getStartPoint()
    {
        return startPoint;
    }
    
    /**
     * @param startPoint the startPoint to set
     */
    public void setStartPoint(String startPoint)
    {
        this.startPoint = startPoint;
    }
    
    /**
     * @return the endPoint
     */
    public String getEndPoint()
    {
        return endPoint;
    }
    
    /**
     * @param endPoint the endPoint to set
     */
    public void setEndPoint(String endPoint)
    {
        this.endPoint = endPoint;
    }
    
    /**
     * @return the startLatitude
     */
    public double getStartLatitude()
    {
        return startLatitude;
    }
    
    /**
     * @param startLatitude the startLatitude to set
     */
    public void setStartLatitude(double startLatitude)
    {
        this.startLatitude = startLatitude;
    }
    
    /**
     * @return the startLongitude
     */
    public double getStartLongitude()
    {
        return startLongitude;
    }
    
    /**
     * @param startLongitude the startLongitude to set
     */
    public void setStartLongitude(double startLongitude)
    {
        this.startLongitude = startLongitude;
    }
    
    /**
     * @return the endLatitude
     */
    public double getEndLatitude()
    {
        return endLatitude;
    }
    
    /**
     * @param endLatitude the endLatitude to set
     */
    public void setEndLatitude(double endLatitude)
    {
        this.endLatitude = endLatitude;
    }
    
    /**
     * @return the endLongitude
     */
    public double getEndLongitude()
    {
        return endLongitude;
    }
    
    /**
     * @param endLongitude the endLongitude to set
     */
    public void setEndLongitude(double endLongitude)
    {
        this.endLongitude = endLongitude;
    }
    
    /**
     * @return the confirmationDateTime
     */
    public Timestamp getConfirmationDateTime()
    {
        return confirmationDateTime;
    }
    
    /**
     * @param confirmationDateTime the confirmationDateTime to set
     */
    public void setConfirmationDateTime(Timestamp confirmationDateTime)
    {
        this.confirmationDateTime = confirmationDateTime;
    }
    
    /**
     * @return the cancellationDateTime
     */
    public Timestamp getCancellationDateTime()
    {
        return cancellationDateTime;
    }
    
    /**
     * @param cancellationDateTime the cancellationDateTime to set
     */
    public void setCancellationDateTime(Timestamp cancellationDateTime)
    {
        this.cancellationDateTime = cancellationDateTime;
    }
    
    /**
     * @return the actorOnLastChange
     */
    public Role getActorOnLastChange()
    {
        return actorOnLastChange;
    }
    
    /**
     * @param actorOnLastChange the actorOnLastChange to set
     */
    public void setActorOnLastChange(Role actorOnLastChange)
    {
        this.actorOnLastChange = actorOnLastChange;
    }
    
    public Long getTripId()
    {
        return tripId;
    }
    
    public void setTripId(Long long1)
    {
        this.tripId = long1;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((actorOnLastChange == null) ? 0 : actorOnLastChange.hashCode());
        result = prime * result + ((cancellationDateTime == null) ? 0 : cancellationDateTime.hashCode());
        result = prime * result + ((confirmationDateTime == null) ? 0 : confirmationDateTime.hashCode());
        long temp;
        temp = Double.doubleToLongBits(endLatitude);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(endLongitude);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((endPoint == null) ? 0 : endPoint.hashCode());
        temp = Double.doubleToLongBits(startLatitude);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(startLongitude);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((startPoint == null) ? 0 : startPoint.hashCode());
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
        BookingWebServiceModel other = (BookingWebServiceModel) obj;
        if (actorOnLastChange != other.actorOnLastChange)
            return false;
        if (cancellationDateTime == null) {
            if (other.cancellationDateTime != null)
                return false;
        } else if (!cancellationDateTime.equals(other.cancellationDateTime))
            return false;
        if (confirmationDateTime == null) {
            if (other.confirmationDateTime != null)
                return false;
        } else if (!confirmationDateTime.equals(other.confirmationDateTime))
            return false;
        if (Double.doubleToLongBits(endLatitude) != Double.doubleToLongBits(other.endLatitude))
            return false;
        if (Double.doubleToLongBits(endLongitude) != Double.doubleToLongBits(other.endLongitude))
            return false;
        if (endPoint == null) {
            if (other.endPoint != null)
                return false;
        } else if (!endPoint.equals(other.endPoint))
            return false;
        if (Double.doubleToLongBits(startLatitude) != Double.doubleToLongBits(other.startLatitude))
            return false;
        if (Double.doubleToLongBits(startLongitude) != Double.doubleToLongBits(other.startLongitude))
            return false;
        if (startPoint == null) {
            if (other.startPoint != null)
                return false;
        } else if (!startPoint.equals(other.startPoint))
            return false;
        return true;
    }    
}
