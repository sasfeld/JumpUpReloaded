/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.verhicle.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import de.htw.fb4.imi.jumpup.entities.AbstractEntity;
import de.htw.fb4.imi.jumpup.trip.entities.Trip;

/**
 * <p>
 * Basic Entity for Vehicles.
 * </p>
 * 
 * @author <a href="mailto:m_seidler@hotmail.de">Marco Seidler</a>
 * @since 03.01.2015
 * 
 */

@Entity
public class Vehicle extends AbstractEntity
{

    /**
     * 
     */
    private static final long serialVersionUID = 6527900884514589268L;

    @OneToMany
    @JoinColumn(name = "vehicle")
    public List<Trip> trips;
}
