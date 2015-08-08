/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.trip.graph;

import static org.junit.Assert.*;

import org.junit.Test;

import de.htw.fb4.imi.jumpup.util.math.Coordinates;

/**
 * <p></p>
 *
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 08.08.2015
 *
 */
public class VertexTest
{
    private static final double DELTA = 1e-15;
    
    @Test
    /**
     * Vertices should have some tolerance when they calculate their ID to increse the probability to find intersections between trips.
     */
    public void testCalculateIdTolerance()
    {
        Coordinates coordinate1 = new Coordinates(55.1235f, 49.1235f);        
        Coordinates coordinate2 = new Coordinates(55.1239f, 49.1239f);        
        Coordinates coordinate3 = new Coordinates(55.122f, 49.122f);
        
        Vertex vertex1 = new Vertex(coordinate1);
        Vertex vertex2 = new Vertex(coordinate2);
        Vertex vertex3 = new Vertex(coordinate3);
        
        // due to the closeness of coordinate 1 & 2, they should get the same ID internally...
        assertEquals(vertex1.getId(), vertex2.getId(), DELTA);
        assertNotEquals(vertex1.getId(), vertex3.getId());
        assertNotEquals(vertex2.getId(), vertex3.getId());
        
    }

}
