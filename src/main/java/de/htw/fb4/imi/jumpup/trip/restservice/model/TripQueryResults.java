/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.trip.restservice.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 26.01.2015
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class TripQueryResults
{
    protected List<SingleTripQueryResult> trips = new ArrayList<SingleTripQueryResult>();

    /**
     * @return the trips
     */
    public List<SingleTripQueryResult> getTrips()
    {
        return trips;
    }

    /**
     * @param trips the trips to set
     */
    public void setTrips(List<SingleTripQueryResult> trips)
    {
        this.trips = trips;
    }
}
