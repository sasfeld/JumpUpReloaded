/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.trip.graph.shortest;

import de.htw.fb4.imi.jumpup.trip.graph.Vertex;

/**
 * <p>Exception thrown if a path from an origin {@link Vertex} to a destination {@link Vertex} was not found.</p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 09.08.2015
 *
 */
public class PathNotFoundException extends Exception
{
    /**
     * 
     */
    private static final long serialVersionUID = -708759877010500743L;
    private Vertex origin;
    private Vertex destination;
    
    public PathNotFoundException(Vertex origin, Vertex destination)
    {
        super("Route from " + origin.getCoordinates() + " to " + destination.getCoordinates() + " was not found.");
        this.origin = origin;
        this.destination = destination;
    }

    /**
     * @return the origin
     */
    public Vertex getOrigin()
    {
        return origin;
    }

    /**
     * @return the destination
     */
    public Vertex getDestination()
    {
        return destination;
    }
}
