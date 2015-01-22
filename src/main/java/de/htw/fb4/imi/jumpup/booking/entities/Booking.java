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

}
