/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.booking.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import de.htw.fb4.imi.jumpup.entities.AbstractEntity;
import de.htw.fb4.imi.jumpup.trip.entities.Trip;
import de.htw.fb4.imi.jumpup.user.entities.User;

/**
 * <p>
 * Basis Entity for Bookings.
 * </p>
 * 
 * @author <a href="mailto:m_seidler@hotmail.de">Marco Seidler</a>
 * @since 22.01.2015
 * 
 */

@Entity
@Table(name = "booking")
@NamedQueries({
        @NamedQuery(name = Booking.NAME_QUERY_BY_STARTPOINT, query = "SELECT b FROM Booking b WHERE b.startpoint = :startpoint"),
        @NamedQuery(name = Booking.NAME_QUERY_BY_ENDPOINT, query = "SELECT b FROM Booking b WHERE b.endpoint = :endpoint") })
public class Booking extends AbstractEntity
{

    public static final String NAME_QUERY_BY_STARTPOINT = "QUERY_BY_STARTPOINT";
    public static final String NAME_QUERY_BY_ENDPOINT = "QUERY_BY_ENDPOINT";

    /**
     * 
     */
    private static final long serialVersionUID = 3235003970981904704L;

    @Column(name = "startpoint", nullable = false, updatable = true, unique = false)
    private String startPoint;

    @Column(name = "endpoint", nullable = false, updatable = true, unique = false)
    private String endPoint;

    @Column(name = "startLatitude", nullable = false, updatable = true, unique = false)
    private double startLatitude;

    @Column(name = "startLongitude", nullable = false, updatable = true, unique = false)
    private double startLongitude;

    @Column(name = "endLatitude", nullable = false, updatable = true, unique = false)
    private double endLatitude;

    @Column(name = "endLongitude", nullable = false, updatable = true, unique = false)
    private double endLongitude;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookings")
    private Trip trip;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookings")
    private User passenger;

    public String getStartPoint()
    {
        return startPoint;
    }

    public void setStartPoint(String startPoint)
    {
        this.startPoint = startPoint;
    }

    public String getEndPoint()
    {
        return endPoint;
    }

    public void setEndPoint(String endPoint)
    {
        this.endPoint = endPoint;
    }

    public double getStartLatitude()
    {
        return startLatitude;
    }

    public void setStartLatitude(double startLatitude)
    {
        this.startLatitude = startLatitude;
    }

    public double getStartLongitude()
    {
        return startLongitude;
    }

    public void setStartLongitude(double startLongitude)
    {
        this.startLongitude = startLongitude;
    }

    public double getEndLatitude()
    {
        return endLatitude;
    }

    public void setEndLatitude(double endLatitude)
    {
        this.endLatitude = endLatitude;
    }

    public double getEndLongitude()
    {
        return endLongitude;
    }

    public void setEndLongitude(double endLongitude)
    {
        this.endLongitude = endLongitude;
    }

    public Trip getTrip()
    {
        return trip;
    }

    public void setTrip(Trip trip)
    {
        this.trip = trip;
    }

    public User getPassenger()
    {
        return passenger;
    }

    public void setPassenger(User passenger)
    {
        this.passenger = passenger;
    }

    @Override
    public String toString()
    {
        return "Booking [startPoint=" + startPoint + ", endPoint=" + endPoint
                + ", startLatitude=" + startLatitude + ", startLongitude="
                + startLongitude + ", endLatitude=" + endLatitude
                + ", endLongitude=" + endLongitude + ", trip=" + trip
                + ", passenger=" + passenger + "]";
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(endLatitude);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(endLongitude);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result
                + ((endPoint == null) ? 0 : endPoint.hashCode());
        result = prime * result
                + ((passenger == null) ? 0 : passenger.hashCode());
        temp = Double.doubleToLongBits(startLatitude);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(startLongitude);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result
                + ((startPoint == null) ? 0 : startPoint.hashCode());
        result = prime * result + ((trip == null) ? 0 : trip.hashCode());
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
        Booking other = (Booking) obj;
        if (Double.doubleToLongBits(endLatitude) != Double
                .doubleToLongBits(other.endLatitude))
            return false;
        if (Double.doubleToLongBits(endLongitude) != Double
                .doubleToLongBits(other.endLongitude))
            return false;
        if (endPoint == null) {
            if (other.endPoint != null)
                return false;
        } else if (!endPoint.equals(other.endPoint))
            return false;
        if (passenger == null) {
            if (other.passenger != null)
                return false;
        } else if (!passenger.equals(other.passenger))
            return false;
        if (Double.doubleToLongBits(startLatitude) != Double
                .doubleToLongBits(other.startLatitude))
            return false;
        if (Double.doubleToLongBits(startLongitude) != Double
                .doubleToLongBits(other.startLongitude))
            return false;
        if (startPoint == null) {
            if (other.startPoint != null)
                return false;
        } else if (!startPoint.equals(other.startPoint))
            return false;
        if (trip == null) {
            if (other.trip != null)
                return false;
        } else if (!trip.equals(other.trip))
            return false;
        return true;
    }

}