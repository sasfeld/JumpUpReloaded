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
    public String startpoint;

    @Column(name = "endpoint", nullable = false, updatable = true, unique = false)
    public String endpoint;

    @Column(name = "latstartpoint", nullable = false, updatable = true, unique = false)
    public float latStartpoint;

    @Column(name = "longstartpoint", nullable = false, updatable = true, unique = false)
    public float longStartpoint;

    @Column(name = "latendpoint", nullable = false, updatable = true, unique = false)
    public float latEntpoint;

    @Column(name = "longendpoint", nullable = false, updatable = true, unique = false)
    public float longEndpoint;

    @Column(name = "startdate", nullable = false, updatable = true, unique = false)
    public Date startDate;

    @Column(name = "enddate", nullable = false, updatable = true, unique = false)
    public Date endDate;

    @Column(name = "price", nullable = false, updatable = true, unique = false)
    public float price;

    @Column(name = "overviewpath", nullable = false, updatable = true, unique = false)
    public String overViewPath;

    @Column(name = "viawaypoints", nullable = false, updatable = true, unique = false)
    public String viaWaypoints;

    @Column(name = "numberofseats", nullable = false, updatable = true, unique = false)
    public Byte numberOfSeats;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trips")
    public Vehicle vehicle;

}
